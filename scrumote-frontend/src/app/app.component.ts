import {Component} from '@angular/core';
import {AuthenticationService} from "./_services";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {

  constructor(private authenticationService: AuthenticationService) {
    this.authenticationService.authenticate(undefined, undefined, undefined);
  }

  authenticated() {
    return this.authenticationService.authenticated;
  }

  logout() {
    this.authenticationService.logout();
  }
}