import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  AlertService,
  AuthenticationService,
  NotificationsService,
  PlanningService
} from '../../_services';
import {Planning} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {Subscription} from 'rxjs';
import {
  PlanningCreatedEvent,
  PlanningDeletedEvent,
  PlanningFinishedEvent,
  PlanningUpdatedEvent
} from '../../_events';

@Component({
  selector: 'app-planning-list',
  templateUrl: 'planning-list.component.html',
  styleUrls: ['./planning-list.component.css']
})
export class PlanningListComponent implements OnInit, OnDestroy {

  private subscriptions = new Subscription();

  readonly listType!: string;

  dataSource = new MatTableDataSource<Planning>();

  displayedColumns: string[]
      = ['code', 'name', 'description', 'deckName', 'moderatorUsername', 'finished', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private planningService: PlanningService,
              private notifications: NotificationsService) {
    if (this.route.snapshot.url[0].path === 'all-plannings') {
      this.listType = 'all';
    } else if (this.route.snapshot.url[0].path === 'my-plannings') {
      this.listType = 'my';
    }
  }

  private loadPlannings() {
    if (this.listType === 'all') {
      this.planningService.getAllPlannings().subscribe((response: Planning[]) => {
        this.dataSource.data = response;
      });
    } else if (this.listType === 'my') {
      this.planningService.getMyPlannings().subscribe((response: Planning[]) => {
        this.dataSource.data = response;
      });
    }
  }

  // region Init & Destroy
  ngOnInit() {
    this.subscribeNotifications();
    this.loadPlannings();
    this.dataSource.paginator = this.paginator;
  }

  ngOnDestroy() {
    this.unsubscribeNotifications();
  }

  // endregion

  // region Notifications
  private subscribeNotifications() {
    this.subscriptions.add(this.notifications.planningCreatedEvent.subscribe(
        event => this.planningCreatedEventHandler(event)));
    this.subscriptions.add(this.notifications.planningFinishedEvent.subscribe(
        event => this.planningFinishedEventHandler(event)));
    this.subscriptions.add(this.notifications.planningUpdatedEvent.subscribe(
        event => this.planningUpdatedEventHandler(event)));
    this.subscriptions.add(this.notifications.planningDeletedEvent.subscribe(
        event => this.planningDeletedEventHandler(event)));
  }

  private unsubscribeNotifications() {
    this.subscriptions.unsubscribe();
  }

  private planningCreatedEventHandler(event: PlanningCreatedEvent) {
    this.loadPlannings();
    this.alert.success('planningList.planningCreated');
  }

  private planningFinishedEventHandler(event: PlanningFinishedEvent) {
    this.loadPlannings();
    this.alert.success('planningList.planningFinished');
  }

  private planningUpdatedEventHandler(event: PlanningUpdatedEvent) {
    this.loadPlannings();
    this.alert.success('planningList.planningUpdated');
  }

  private planningDeletedEventHandler(event: PlanningDeletedEvent) {
    this.loadPlannings();
    this.alert.success('planningList.planningDeleted');
  }

  // endregion

  openPlanning(planning: Planning) {
    this.planningService.planningToOpen = planning;
    this.router.navigate(['/planning']);
  }

  editPlanning(planning: Planning) {
    this.planningService.planningToEdit = planning;
    this.router.navigate(['/edit-planning']);
  }

  deletePlanning(planning: Planning) {
    this.planningService.deletePlanning(planning).subscribe(() => {
      this.loadPlannings();
      this.alert.success('planningList.delete.success');
    });
  }

  refreshPage() {
    if (this.listType === 'all') {
      this.router.navigate(['/all-plannings']);
    } else if (this.listType === 'my') {
      this.router.navigate(['/my-plannings']);
    }
  }
}
