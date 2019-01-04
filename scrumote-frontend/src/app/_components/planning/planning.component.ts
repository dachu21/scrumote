import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, PlanningService} from '../../_services';
import {Planning} from '../../_models';

@Component({
  selector: 'app-planning',
  templateUrl: 'planning.component.html',
  styleUrls: ['./planning.component.css']
})
export class PlanningComponent implements OnInit {

  planning!: Planning;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private planningService: PlanningService) {

    const planningToOpen = this.planningService.planningToOpen;
    this.planningService.planningToEdit = undefined;
    if (!planningToOpen) {
      this.alert.error('planning.noPlanningLoaded');
      this.router.navigate(['/home']);
    } else {
      this.planning = planningToOpen;
    }
  }

  ngOnInit() {
    this.refreshPlanning();
  }

  refreshPlanning() {
    if (this.planning.id) {
      this.planningService.getPlanning(this.planning.id).subscribe((response: Planning) => {
        this.planning = response;
      });
    }
  }
}
