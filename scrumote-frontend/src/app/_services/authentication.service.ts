import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {finalize} from "rxjs/operators";

@Injectable()
export class AuthenticationService {

  authenticated: boolean = false;

  constructor(private http: HttpClient,
              private router: Router) {
  }

  authenticate(username: string | undefined, password: string | undefined, callback: (() => void) | undefined) {

    const headers = new HttpHeaders(username && password ? {
      authorization: 'Basic ' + btoa(username + ':' + password)
    } : {});

    this.http.get<LoggedUser>('/api/login', {headers: headers}).subscribe((response: LoggedUser) => {
      this.authenticated = !!response.name;
      return callback && callback();
    });
  }

  logout() {
    this.http.post('logout', {}).pipe(finalize(() => {
      this.authenticated = false;
      this.router.navigateByUrl('/login');
    })).subscribe();
  }
}
