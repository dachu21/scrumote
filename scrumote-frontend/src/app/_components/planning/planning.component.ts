import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  AlertService,
  AuthenticationService,
  IssueService,
  PlanningService,
  UserService
} from '../../_services';
import {Issue, Planning, User} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-planning',
  templateUrl: 'planning.component.html',
  styleUrls: ['./planning.component.css'],
  animations: [
    trigger('expandTrigger', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition(
          'expanded <=> collapsed',
          animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class PlanningComponent implements OnInit {

  openedPlanning!: Planning;

  expandedIssue?: Issue | null;
  issuesDataSource = new MatTableDataSource<Issue>();
  issuesDisplayedColumns: string[]
      = ['code', 'name', 'finishedIterations', 'estimate', 'active', 'actions'];

  usersDataSource = new MatTableDataSource<User>();
  usersDisplayedColumns: string[]
      = [''];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private planningService: PlanningService,
              private issueService: IssueService,
              private userService: UserService) {

    const planningToOpen = this.planningService.planningToOpen;
    this.planningService.planningToEdit = undefined;
    if (!planningToOpen) {
      this.alert.error('openedPlanning.noPlanningLoaded');
      this.router.navigate(['/home']);
    } else {
      this.openedPlanning = planningToOpen;
    }
  }

  ngOnInit() {
    this.refreshPlanning();
    this.refreshIssues();
    this.refreshUsers();
    this.issuesDataSource.paginator = this.paginator;
  }

  refreshPlanning() {
    if (this.openedPlanning.id) {
      this.planningService.getPlanning(this.openedPlanning.id).subscribe((response: Planning) => {
        this.openedPlanning = response;
      });
    }
  }

  refreshIssues() {
    if (this.openedPlanning.id) {
      this.issueService.getIssuesForPlanning(this.openedPlanning.id).subscribe((response: Issue[]) => {
        this.issuesDataSource.data = response;
      });
    }
  }

  refreshUsers() {
    if (this.openedPlanning.id) {
      this.userService.getUsersForPlanning(this.openedPlanning.id).subscribe((response: User[]) => {
        this.usersDataSource.data = response;
      });
    }
  }

  finishPlanning() {
    this.planningService.finishPlanning(this.openedPlanning).subscribe(() => {
      this.alert.success('openedPlanning.finish.success');
    });
    this.refreshPlanning();
  }

  createIssue() {
    this.issueService.openedPlanning = this.openedPlanning;
    this.router.navigate(['/create-issue']);
  }

  editIssue(issue: Issue) {
    this.issueService.openedPlanning = this.openedPlanning;
    this.issueService.issueToEdit = issue;
    this.router.navigate(['/edit-issue']);
  }

  activateIssue(issue: Issue) {
    if (this.openedPlanning.id) {
      this.issueService.activateIssue(this.openedPlanning.id, issue).subscribe(() => {
        this.refreshIssues();
        this.alert.success('openedPlanning.activateIssue.success');
      });
    }
  }

  deleteIssue(issue: Issue) {
    if (this.openedPlanning.id) {
      this.issueService.deleteIssue(this.openedPlanning.id, issue).subscribe(() => {
        this.refreshIssues();
        this.alert.success('openedPlanning.deleteIssue.success');
      });
    }
  }

  expandIssue(issue: Issue) {
    if (this.expandedIssue !== issue && issue.id) {
    } else {
      this.expandedIssue = null;
    }
  }
}
