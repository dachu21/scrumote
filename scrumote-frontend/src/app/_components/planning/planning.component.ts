import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, IssueService, PlanningService} from '../../_services';
import {Issue, Planning} from '../../_models';

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
              readonly auth: AuthenticationService,
              private planningService: PlanningService,
              private issueService: IssueService) {

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

  createIssue() {
    this.issueService.openedPlanning = this.planning;
    this.router.navigate(['/create-issue']);
  }

  finishPlanning() {
    this.planningService.finishPlanning(this.planning).subscribe(() => {
      this.alert.success('planning.finish.success');
    });
    this.refreshPlanning();
  }

  editIssue(issue: Issue) {
    this.issueService.openedPlanning = this.planning;
    this.issueService.issueToEdit = issue;
    this.router.navigate(['/edit-issue']);
  }
}
