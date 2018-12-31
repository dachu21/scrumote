import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {AuthenticationService} from './_services';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {


  constructor(
      readonly auth: AuthenticationService,
      readonly router: Router,
      private translateService: TranslateService
  ) {
    this.auth.authenticate();
    translateService.setDefaultLang('en');
    translateService.use('en');
  }

  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };

    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.router.navigated = false;
        window.scrollTo(0, 0);
      }
    });
  }
}
