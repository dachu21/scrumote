import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {AuthenticationService} from '../../_services';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
})
export class ErrorComponent {

  readonly error404: boolean;

  constructor(private auth: AuthenticationService, private route: ActivatedRoute) {
    this.error404 = !!route.snapshot.data.error404;
  }
}
