import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, UserService} from '../../_services';
import {User} from '../../_models';
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
  ],
})
export class UserListComponent implements OnInit {

  dataSource = new MatTableDataSource<User>();
  expandedElement?: User;

  displayedColumns: string[]
      = ['username', 'email', 'firstName', 'lastName', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(readonly auth: AuthenticationService, private router: Router,
              private userService: UserService, private route: ActivatedRoute,
              private alert: AlertService) {
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
    this.router.navigate(['/edit-user']);
  }
}
