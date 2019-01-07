import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {AuthenticationService, SystemFeatureService} from '../_services';
import {SystemFeature} from '../_models';


@Injectable()
export class SystemFeatureGuard implements CanActivate {

  constructor(
      private auth: AuthenticationService,
      private router: Router,
      private systemFeatureService: SystemFeatureService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    if (!this.auth.afterAuthenticateCall) {
      this.auth.guardUrl = state.url;
      return Observable.create(false);
    }

    return this.systemFeatureService.getSystemFeature(route.data.systemFeature)
    .pipe(map((response: SystemFeature) => {
      if (response.enabled) {
        return true;
      } else {
        let redirectUrl;
        if (!this.auth.isAuthenticated()) {
          redirectUrl = '/login';
        } else {
          redirectUrl = '/error';
        }
        this.router.navigate([redirectUrl], {queryParams: {path: state.url}});
        return false;
      }
    }));
  }
}
