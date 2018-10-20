import {Component} from '@angular/core';
import {AuthenticationService} from "./_services";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {

  constructor(private authenticationService: AuthenticationService,
              private translateService: TranslateService) {
    this.authenticationService.authenticate(undefined, undefined, undefined);
    translateService.setDefaultLang('en');
    translateService.use('en');
  }

  authenticated() {
    return this.authenticationService.authenticated;
  }

  logout() {
    this.authenticationService.logout();
  }
}