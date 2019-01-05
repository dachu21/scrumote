import {Component, OnInit} from '@angular/core';
import {AuthenticationService, LanguageService} from './_services';
import {NavigationEnd, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(
      private router: Router,
      readonly auth: AuthenticationService,
      readonly language: LanguageService) {

    this.auth.authenticate();
    this.language.init();
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
