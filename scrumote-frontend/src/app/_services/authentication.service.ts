import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {finalize} from 'rxjs/operators';
import {SessionInfo} from '../_models';

@Injectable()
export class AuthenticationService {

  afterAuthenticateCall = false;
  guardUrl?: string;

  private authenticated = false;
  private sessionInfo = SessionInfo.createAnonymous();

  constructor(private http: HttpClient, private router: Router) {
  }

  authenticate(username?: string, password?: string, path?: string) {

    const headers = new HttpHeaders(username && password ? {
      authorization: 'Basic ' + btoa(username + ':' + password)
    } : {});

    this.http
    .get<SessionInfo>('/login', {headers: headers})
    .subscribe((response: SessionInfo) => {

      if (!!response.id && !!response.username && !!response.authorities) {
        this.authenticated = true;
        this.sessionInfo = response;
      } else {
        this.authenticated = false;
      }
    })
    .add(() => {
      this.afterAuthenticateCall = true;

      if (this.authenticated && path) {
        this.router.navigate([path]);
      }
      if (this.guardUrl) {
        this.router.navigate([this.guardUrl]);
        this.guardUrl = undefined;
      }
    });
  }

  logout() {
    this.http.post('/logout', {}).pipe(finalize(() => {
      this.authenticated = false;
      this.router.navigateByUrl('/login');
    })).subscribe();
  }

  isAuthenticated() {
    return this.authenticated;
  }

  hasAuthority(authority: string) {
    return this.authenticated && this.sessionInfo.authorities.includes(authority);
  }

  getUsername() {
    return this.sessionInfo.username;
  }
}
