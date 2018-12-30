import {Component} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {AuthenticationService} from './_services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {


  constructor(
      readonly auth: AuthenticationService,
      readonly router: Router,
      private translateService: TranslateService
  ) {
    this.auth.authenticate();
    translateService.setDefaultLang('en');
    translateService.use('en');
  }
}
