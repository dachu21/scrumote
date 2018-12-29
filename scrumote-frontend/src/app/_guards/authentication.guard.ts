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

    if (route.data.authenticated !== this.auth.isAuthenticated()) {
      this.router.navigate(
          [route.data.fallbackUrl],
          {queryParams: {path: state.url}});
      return false;
    }

    return true;
  }
}
