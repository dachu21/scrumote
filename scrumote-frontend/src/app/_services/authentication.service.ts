import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {finalize} from "rxjs/operators";
import {Router} from "@angular/router";

@Injectable()
export class AuthenticationService {

  authenticated = false;

  constructor(private http: HttpClient, private router: Router) {
  }

  authenticate(username, password, callback) {

    const headers = new HttpHeaders(username && password ? {
      authorization: 'Basic ' + btoa(username + ':' + password)
    } : {});

    this.http.get('/api/login', {headers: headers}).subscribe(response => {
      if (response['name']) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
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
