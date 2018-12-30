import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {AlertService, PlanningService} from '../../_services';
import {Planning} from '../../_models';

@Component({
  selector: 'app-planning-list',
  templateUrl: 'planning-list.component.html'
})
export class PlanningListComponent {

  plannings!: Array<Planning>;

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private planningService: PlanningService,
      private alert: AlertService) {
    planningService.getAllPlannings().subscribe((response: Array<Planning>) => {
      this.plannings = response;
    });
  }

}
