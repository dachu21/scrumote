import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../_services';

@Injectable()
export class AuthorizationGuard implements CanActivate {

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
    if (!this.auth.isAuthenticated()) {
      redirectUrl = '/login';
    } else if (!this.auth.hasAuthority(route.data.requiredAuthority)) {
      redirectUrl = '/error';
    }

    if (redirectUrl) {
      this.router.navigate([redirectUrl], {queryParams: {path: state.url}});
      return false;
    }

    return true;
  }
}
