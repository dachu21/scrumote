import {Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {AlertService} from '../_services';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html'
})

export class AlertComponent implements OnDestroy {

  message: any;
  private subscription: Subscription;

  constructor(private alertService: AlertService) {
    this.subscription = this.alertService.getMessage().subscribe(message => {
      this.message = message;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}