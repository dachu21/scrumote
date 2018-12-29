import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class AuthorizationGuard implements CanActivate {

  constructor(
      private auth: AuthenticationService,
      private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.auth.afterAuthenticateCall) {
      this.auth.authorityGuardUrl = state.url;
      return false;
    }

    if (!this.auth.hasAuthority(route.data.requiredAuthority)) {
      this.router.navigate(
          [route.data.fallbackUrl],
          {queryParams: {path: state.url}});
      return false;
    }

    return true;
  }
}
