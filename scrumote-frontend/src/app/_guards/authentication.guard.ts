import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../_services';

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(
      private auth: AuthenticationService,
      private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.auth.afterAuthenticateCall) {
      this.auth.guardUrl = state.url;
      return false;
    }

    let redirectUrl;
    if (route.data.authenticated === true && !this.auth.isAuthenticated()) {
      redirectUrl = '/login';
    } else if (route.data.authenticated === false && this.auth.isAuthenticated()) {
      redirectUrl = '/error';
    }

    if (redirectUrl) {
      this.router.navigate([redirectUrl], {queryParams: {path: state.url}});
      return false;
    }

    return true;
  }
}
