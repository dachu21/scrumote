import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, UserService, UserStatsService} from '../../_services';
import {User, UserStats} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-user-list',
  templateUrl: 'user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  animations: [
    trigger('expandTrigger', [
      state('collapsed', style({height: '0px', minHeight: '0', display: 'none'})),
      state('expanded', style({height: '*'})),
      transition(
          'expanded <=> collapsed',
          animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class UserListComponent implements OnInit {

  expandedUser?: User | null;

  dataSource = new MatTableDataSource<User>();
  displayedColumns: string[]
      = ['username', 'email', 'firstName', 'lastName', 'actions'];

  statsDataSource = new MatTableDataSource<UserStats>();
  statsDisplayedColumns: string[]
      = ['plannings', 'issues', 'votes', 'firstVotesBelowEstimate', 'firstVotesAboveEstimate',
    'firstVotesEqualEstimate', 'averageFirstVoteLevelDifference'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private userService: UserService,
              private userStatsService: UserStatsService) {
  }

  private refreshDataSource() {
    this.userService.getAllUsers().subscribe((response: User[]) => {
      this.dataSource.data = response;
    });
  }

  ngOnInit() {
    this.refreshDataSource();
    this.dataSource.paginator = this.paginator;
  }

  editUser(user: User) {
    this.userService.userToEdit = user;
    this.router.navigate(['/edit-any-user']);
  }

  editUsersPassword(user: User) {
    this.userService.userToEdit = user;
    this.router.navigate(['/edit-any-password']);
  }

  onClickRow(element: User) {
    if (this.expandedUser !== element && element.id) {
      this.userStatsService.getAnyUserStats(element.id).subscribe((response: UserStats) => {
        this.expandedUser = element;
        this.statsDataSource.data = [response];
      });
    } else {
      this.expandedUser = null;
    }
  }

  refreshPage() {
    this.router.navigate(['/users']);
  }
}
