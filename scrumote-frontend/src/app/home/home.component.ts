import {Component} from '@angular/core';
import {AuthenticationService} from '../_services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent {

  constructor(private authenticationService: AuthenticationService) {
  }

  authenticated() {
    return this.authenticationService.authenticated;
  }

}
