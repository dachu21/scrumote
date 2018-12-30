import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {AuthenticationService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {


  constructor(readonly auth: AuthenticationService, private translateService: TranslateService) {
    this.auth.authenticate();
    translateService.setDefaultLang('en');
    translateService.use('en');
  }
}
