import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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
import {MatDialog, MatPaginator, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';
// noinspection TypeScriptPreferShortImport
import {VoteDialogComponent} from '../vote-dialog/vote-dialog.component';
import {Subscription} from 'rxjs';

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

  private DIALOG_WIDTH = '300px';
  private subscriptions = new Subscription();

  openedPlanning!: Planning;
  deck!: Deck;
  expandedIssue: Issue | null = null;

  issuesDataSource = new MatTableDataSource<Issue>();
  issuesDisplayedColumns: string[]
      = ['code', 'name', 'finishedIterations', 'estimate', 'active', 'actions'];

  usersDataSource = new MatTableDataSource<User>();
  usersDisplayedColumns: string[] = [];
  finishedIterationsColumns: string[] = [];

  issueVotesMap = new Map<number, Map<number, string>>(); // <userId, <iteration, vote>>

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private dialog: MatDialog,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private planningService: PlanningService,
              private issueService: IssueService,
              private userService: UserService,
              private deckService: DeckService,
              private voteService: VoteService,
              private notifications: NotificationsService) {

    const planningToOpen = this.planningService.planningToOpen;
    this.planningService.planningToOpen = undefined;
    if (!planningToOpen) {
      this.alert.error('openedPlanning.noPlanningLoaded');
      this.router.navigate(['/home']);
    } else {
      this.openedPlanning = planningToOpen;
    }
  }

  ngOnInit() {
    console.log('ON INIT');
    this.subscribeNotifications();

    this.refreshPlanning();
    this.refreshIssues();
    this.refreshUsers();
    this.refreshDeck();

    this.issuesDataSource.paginator = this.paginator;
  }

  ngOnDestroy(): void {
    console.log('ON DESTROY');
    this.unsubscribeNotifications();
  }

  // region Notifications
  private subscribeNotifications() {

    this.subscriptions.add(this.notifications.allUsersVotedEvent.subscribe(event => {
      console.log('allUsersVoted event read.');
      if (this.expandedIssue && this.expandedIssue.finishedIterations &&
          this.openedPlanning.id === event.planningId &&
          this.expandedIssue.id === event.issueId &&
          this.expandedIssue.finishedIterations + 1 === event.iteration) {
        this.refreshIssues();
        this.refreshExpandedIssueVotesMap();
        this.refreshExpandedIssueDisplayedColumns();
        this.alert.success('openedPlanning.allUsersVoted');
        console.log('all users voted success.');
      }
    }));
  }

  private unsubscribeNotifications() {
    this.subscriptions.unsubscribe();
  }

  // endregion

  // region Refresh data
  private refreshPlanning() {
    if (this.openedPlanning.id) {
      this.planningService.getPlanning(this.openedPlanning.id).subscribe((response: Planning) => {
        this.openedPlanning = response;
      });
    }
  }

  private refreshIssues() {
    if (this.openedPlanning.id) {
      this.issueService.getIssuesForPlanning(this.openedPlanning.id).subscribe((response: Issue[]) => {
        this.issuesDataSource.data = response;
      });
    }
  }

  private refreshUsers() {
    if (this.openedPlanning.id) {
      this.userService.getUsersForPlanning(this.openedPlanning.id).subscribe((response: User[]) => {
        this.usersDataSource.data = response;
        this.issueVotesMap.clear();
        for (const user of this.usersDataSource.data) {
          if (user.id) {
            this.issueVotesMap.set(user.id, new Map<number, string>());
          }
        }
      });
    }
  }

  private refreshDeck() {
    this.deckService.getDeck(this.openedPlanning.deckId).subscribe((response: Deck) => {
      this.deck = response;
    });
  }

  private refreshExpandedIssueVotesMap() {
    if (this.openedPlanning.id && this.expandedIssue && this.expandedIssue.id) {
      this.voteService.getVotesForIssue(this.openedPlanning.id, this.expandedIssue.id)
      .subscribe((response: Vote[]) => {
        for (const vote of response) {
          if (vote.userId) {
            const userVotesMap = this.issueVotesMap.get(vote.userId);
            if (userVotesMap) {
              userVotesMap.set(vote.iteration, vote.value);
            }
          }
        }
      });
    }
  }

  private refreshExpandedIssueDisplayedColumns() {
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

  // endregion

  // region Planning actions
  finishPlanning() {
    this.planningService.finishPlanning(this.openedPlanning).subscribe(() => {
      this.alert.success('openedPlanning.finish.success');
    });
    this.refreshPlanning();
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
        this.refreshIssues();
        this.alert.success('openedPlanning.activateIssue.success');
      });
    }
  }

  estimateIssue(issue: Issue) {
    const dialogRef = this.dialog.open(VoteDialogComponent, {
      width: this.DIALOG_WIDTH,
      data: {
        deck: this.deck,
        i18nHeaderCode: 'openedPlanning.estimateIssue.header'
      }
    });
    dialogRef.afterClosed().subscribe(cardValue => {
      if (cardValue && this.openedPlanning.id) {
        this.issueService.estimateIssue(this.openedPlanning.id, issue, cardValue).subscribe(() => {
          this.refreshIssues();
          this.alert.success('openedPlanning.estimateIssue.success');
        });
      }
    });
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
      this.expandedIssue = issue;
      this.refreshExpandedIssueVotesMap();
      this.refreshExpandedIssueDisplayedColumns();
    } else {
      this.expandedIssue = null;
    }
  }

  createVote(issue: Issue) {
    const dialogRef = this.dialog.open(VoteDialogComponent, {
      width: this.DIALOG_WIDTH,
      data: {
        deck: this.deck,
        i18nHeaderCode: 'openedPlanning.vote.header'
      }
    });
    dialogRef.afterClosed().subscribe(cardValue => {
      if (cardValue && this.openedPlanning.id && issue.id && issue.finishedIterations != null) {
        const vote = Vote.create(issue.finishedIterations + 1, cardValue);
        this.voteService.createVote(this.openedPlanning.id, issue.id, vote).subscribe(() => {
          this.refreshExpandedIssueVotesMap();
          this.refreshExpandedIssueDisplayedColumns();
          this.alert.success('openedPlanning.vote.success');
        });
      }
    });
  }

  // endregion

  stringToNumber(str: string) {
    return Number(str);
  }
}
