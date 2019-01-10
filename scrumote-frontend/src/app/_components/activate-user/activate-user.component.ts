import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, UserTokenService} from '../../_services';

@Component({
  selector: 'app-activate-user',
  templateUrl: './activate-user.component.html'
})
export class ActivateUserComponent implements OnInit {

  token!: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              private userTokenService: UserTokenService) {

  }

  ngOnInit() {
    const tokenParam = this.route.snapshot.paramMap.get('token');
    this.token = tokenParam ? tokenParam : '';

    this.userTokenService.activateUser(this.token).subscribe(() => {
      this.alert.success('activateUser.success');
      this.router.navigate(['/login']);
    }, error1 => {
      this.router.navigate(['/login']);
    });
  }
}
