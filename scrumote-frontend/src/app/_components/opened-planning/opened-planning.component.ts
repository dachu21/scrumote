import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {Subscription} from 'rxjs';
import {
  AlertService,
  AuthenticationService,
  DeckService,
  IssueService,
  NotificationsService,
  PlanningService,
  UserService,
  VoteService
} from '../../_services';
import {Deck, Issue, Planning, User, Vote} from '../../_models';
import {
  AllUsersVotedEvent,
  IssueActivatedEvent,
  IssueCreatedEvent,
  IssueDeletedEvent,
  IssueEstimatedEvent,
  IssueUpdatedEvent,
  PlanningDeletedEvent,
  PlanningFinishedEvent,
  PlanningUpdatedEvent
} from '../../_events';
// noinspection TypeScriptPreferShortImport
import {DialogService} from '../../_services/dialog.service';

@Component({
  selector: 'app-planning',
  templateUrl: 'opened-planning.component.html',
  styleUrls: ['./opened-planning.component.css'],
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
export class OpenedPlanningComponent implements OnInit, OnDestroy {

  // region Data
  private subscriptions = new Subscription();

  canVote = false;

  openedPlanning!: Planning;
  deck!: Deck;
  usersDataSource = new MatTableDataSource<User>();
  issuesDataSource = new MatTableDataSource<Issue>();

  expandedIssue: Issue | null = null;
  expandedIssueVotesMap = new Map<number, Map<number, string>>(); // <userId, <iteration, vote>>

  issuesDisplayedColumns: string[]
      = ['code', 'name', 'finishedIterations', 'estimate', 'active', 'actions'];
  usersDisplayedColumns: string[] = [];
  finishedIterationsColumns: string[] = [];

  @ViewChild(MatPaginator) issuesPaginator!: MatPaginator;
  @ViewChild(MatSort) issuesSort!: MatSort;

  // endregion

  // region Constructor
  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private planningService: PlanningService,
              private issueService: IssueService,
              private userService: UserService,
              private deckService: DeckService,
              private voteService: VoteService,
              private notifications: NotificationsService,
              private dialogService: DialogService) {

    const planningToOpen = this.planningService.planningToOpen;
    this.planningService.planningToOpen = undefined;
    if (!planningToOpen) {
      this.alert.error('openedPlanning.noPlanningLoaded');
      const url = this.auth.hasAuthority('getAllPlannings') ?
          '/all-plannings' : '/my-plannings';
      this.router.navigate([url]);
    } else {
      this.openedPlanning = planningToOpen;
    }
  }

  // endregion

  // region Init & Destroy
  ngOnInit() {
    this.subscribeNotifications();
    this.initData();
    this.issuesDataSource.paginator = this.issuesPaginator;
    this.issuesDataSource.sort = this.issuesSort;
  }

  private initData() {
    this.loadPlanning();
    this.loadDeck();
    this.loadUsers();
    this.loadAllIssues();
    this.expandedIssue = null;
  }

  ngOnDestroy() {
    this.unsubscribeNotifications();
  }

  // endregion

  // region Notifications
  private subscribeNotifications() {
    this.subscriptions.add(this.notifications.allUsersVotedEvent.subscribe(
        event => this.allUsersVotedEventHandler(event)));
    this.subscriptions.add(this.notifications.issueActivatedEvent.subscribe(
        event => this.issueActivatedEventHandler(event)));
    this.subscriptions.add(this.notifications.issueEstimatedEvent.subscribe(
        event => this.issueEstimatedEventHandler(event)));
    this.subscriptions.add(this.notifications.issueUpdatedEvent.subscribe(
        event => this.issueUpdatedEventHandler(event)));
    this.subscriptions.add(this.notifications.issueDeletedEvent.subscribe(
        event => this.issueDeletedEventHandler(event)));
    this.subscriptions.add(this.notifications.issueCreatedEvent.subscribe(
        event => this.issueCreatedEventHandler(event)));
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

  private allUsersVotedEventHandler(event: AllUsersVotedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      if (this.expandedIssue && this.expandedIssue.id === event.issueId) {
        this.reloadIssue(this.expandedIssue);
        this.alert.event('openedPlanning.events.allUsersVotedExp');
      } else {
        this.loadAllIssues();
        this.expandedIssue = null;
        this.alert.event('openedPlanning.events.allUsersVoted');
      }
    }
  }

  private issueActivatedEventHandler(event: IssueActivatedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      if (this.expandedIssue && this.expandedIssue.id === event.issueId) {
        this.reloadIssue(this.expandedIssue);
        this.canVote = true;
        this.alert.event('openedPlanning.events.issueActivatedExp');
      } else {
        this.loadAllIssues();
        this.expandedIssue = null;
        this.alert.event('openedPlanning.events.issueActivated');
      }
    }
  }

  private issueEstimatedEventHandler(event: IssueEstimatedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      if (this.expandedIssue && this.expandedIssue.id === event.issueId) {
        this.reloadIssue(this.expandedIssue);
        this.alert.event('openedPlanning.events.issueEstimatedExp');
      } else {
        this.loadAllIssues();
        this.expandedIssue = null;
        this.alert.event('openedPlanning.events.issueEstimated');
      }
    }
  }

  private issueUpdatedEventHandler(event: IssueUpdatedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      if (this.expandedIssue && this.expandedIssue.id === event.issueId) {
        this.reloadIssue(this.expandedIssue);
        this.alert.event('openedPlanning.events.issueUpdatedExp');
      } else {
        this.loadAllIssues();
        this.expandedIssue = null;
        this.alert.event('openedPlanning.events.issueUpdated');
      }
    }
  }

  private issueDeletedEventHandler(event: IssueDeletedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      this.loadAllIssues();
      this.expandedIssue = null;
      this.alert.event('openedPlanning.events.issueDeleted');
    }
  }

  private issueCreatedEventHandler(event: IssueCreatedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      this.loadAllIssues();
      this.expandedIssue = null;
      this.alert.event('openedPlanning.events.issueCreated');
    }
  }

  private planningFinishedEventHandler(event: PlanningFinishedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      this.loadPlanning();
      this.alert.event('openedPlanning.events.planningFinished');
    }
  }

  private planningUpdatedEventHandler(event: PlanningUpdatedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      this.loadPlanning();
      this.loadDeck();
      this.loadUsers();
      this.alert.event('openedPlanning.events.planningUpdated');
    }
  }

  private planningDeletedEventHandler(event: PlanningDeletedEvent) {
    if (this.openedPlanning.id === event.planningId) {
      this.alert.event('openedPlanning.events.planningDeleted');
      const url = this.auth.hasAuthority('getAllPlannings') ?
          '/all-plannings' : '/my-plannings';
      this.router.navigate([url]);
    }
  }

  // endregion

  // region Load and assign data
  private loadPlanning() {
    if (this.openedPlanning.id) {
      this.planningService.getPlanning(this.openedPlanning.id).subscribe(
          response => this._assignPlanning(response));
    }
  }

  private _assignPlanning(response: Planning) {
    this.openedPlanning = response;
  }

  private loadDeck() {
    this.deckService.getDeck(this.openedPlanning.deckId).subscribe(
        response => this._assignDeck(response));
  }

  private _assignDeck(response: Deck) {
    this.deck = response;
  }

  private loadUsers() {
    if (this.openedPlanning.id) {
      this.userService.getUsersForPlanning(this.openedPlanning.id).subscribe(
          response => this._assignUsers(response));
    }
  }

  private _assignUsers(response: User[]) {
    this.usersDataSource.data = response;
    this.expandedIssueVotesMap.clear();
    for (const user of this.usersDataSource.data) {
      if (user.id) {
        this.expandedIssueVotesMap.set(user.id, new Map<number, string>());
      }
    }
  }

  private loadAllIssues() {
    if (this.openedPlanning.id) {
      this.issueService.getIssuesForPlanning(this.openedPlanning.id).subscribe(
          response => this._assignAllIssues(response));
    }
  }

  private _assignAllIssues(response: Issue[]) {
    this.issuesDataSource.data = response;
  }

  private reloadIssue(issue: Issue) {
    if (this.openedPlanning.id && issue.id) {
      this.issueService.getIssue(this.openedPlanning.id, issue.id).subscribe(
          response => this._overwriteIssue(issue, response));
    }
  }

  private _overwriteIssue(issue: Issue, response: Issue) {
    Issue.overwrite(issue, response);
    if (this.expandedIssue && this.expandedIssue.id === issue.id) {
      this.reloadExpandedIssueContent();
    }
  }

  private reloadExpandedIssueContent() {
    if (this.openedPlanning.id && this.expandedIssue && this.expandedIssue.id) {
      this.voteService.getVotesForIssue(this.openedPlanning.id, this.expandedIssue.id).subscribe(
          response => this._assignExpandedIssueVotes(response));
    }
    this._setExpandedIssueDisplayedColumns();
  }

  private _assignExpandedIssueVotes(response: Vote[]) {
    for (const vote of response) {
      if (vote.userId) {
        const userVotesMap = this.expandedIssueVotesMap.get(vote.userId);
        if (userVotesMap) {
          userVotesMap.set(vote.iteration, vote.value);
        }
      }
    }
  }

  private _setExpandedIssueDisplayedColumns() {
    this.usersDisplayedColumns = ['username'];
    this.finishedIterationsColumns = [''];
    if (this.expandedIssue && this.expandedIssue.finishedIterations) {
      for (let i = 1; i <= this.expandedIssue.finishedIterations; i++) {
        this.finishedIterationsColumns.push(i.toString());
        this.usersDisplayedColumns.push(i.toString());
      }
    }
    if (this.expandedIssue && this.expandedIssue.active) {
      this.usersDisplayedColumns.push('currentIteration');
    }
  }

  private checkIfCanVote(issue: Issue) {
    if (this.auth.hasAuthority('checkIfMyVoteExists') &&
        issue.active && issue.id && issue.finishedIterations != null && this.openedPlanning.id) {

      this.voteService.checkIfMyVoteExists(this.openedPlanning.id, issue.id,
          issue.finishedIterations + 1).subscribe(
          response => {
            this.canVote = !response;
          });
    } else {
      this.canVote = false;
    }
  }

  // endregion

  // region Planning actions
  finishPlanning() {
    this.dialogService.openAreYouSureDialog().afterClosed().subscribe(value => {
      if (value) {
        this.planningService.finishPlanning(this.openedPlanning).subscribe(() => {
          this.alert.success('openedPlanning.finish.success');
        });
      }
    });
  }

  // endregion

  // region Issue actions
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
        this.alert.success('openedPlanning.activateIssue.success');
      });
    }
  }

  estimateIssue(issue: Issue) {
    this.dialogService.openVoteDialog('openedPlanning.estimateIssue.header', this.deck)
    .afterClosed().subscribe(cardValue => {
      if (cardValue && this.openedPlanning.id) {
        this.issueService.estimateIssue(this.openedPlanning.id, issue, cardValue).subscribe(() => {
          this.alert.success('openedPlanning.estimateIssue.success');
        });
      }
    });
  }

  deleteIssue(issue: Issue) {
    this.dialogService.openAreYouSureDialog().afterClosed().subscribe(value => {
      if (value) {
        if (this.openedPlanning.id) {
          this.issueService.deleteIssue(this.openedPlanning.id, issue).subscribe(() => {
            this.alert.success('openedPlanning.deleteIssue.success');
          });
        }
      }
    });
  }

  expandIssue(issue: Issue) {
    if (this.expandedIssue !== issue && issue.id) {
      this.expandedIssue = issue;
      this.reloadExpandedIssueContent();
      this.checkIfCanVote(issue);
    } else {
      this.expandedIssue = null;
    }
  }

  createVote(issue: Issue) {
    this.dialogService.openVoteDialog('openedPlanning.vote.header', this.deck)
    .afterClosed().subscribe(cardValue => {
      if (cardValue && this.openedPlanning.id && issue.id && issue.finishedIterations != null) {
        const vote = Vote.create(issue.finishedIterations + 1, cardValue);
        this.voteService.createVote(this.openedPlanning.id, issue.id, vote).subscribe(() => {
          this.canVote = false;
          this.alert.success('openedPlanning.vote.success');
        });
      }
    });
  }

  // endregion

  // region Utils

  stringToNumber(str: string) {
    return Number(str);
  }

  refreshPage() {
    this.planningService.planningToOpen = this.openedPlanning;
    this.router.navigate(['/planning']);
  }

  // endregion
}
