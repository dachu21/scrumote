import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
})
export class ErrorComponent {

  readonly error404: boolean;

  constructor(private route: ActivatedRoute) {
    this.error404 = !!route.snapshot.data.error404;
  }
}
