import {Component} from '@angular/core';
import {AuthenticationService} from './_services';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {

  constructor(private auth: AuthenticationService,
              private translateService: TranslateService) {
    this.auth.authenticate();
    translateService.setDefaultLang('en');
    translateService.use('en');
  }
}
