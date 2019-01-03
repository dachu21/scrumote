import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, UserService, UserStatsService} from '../../_services';
import {User, UserStats} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-user-list',
  templateUrl: 'my-stats.component.html',
  styleUrls: ['./my-stats.component.css'],
})
export class MyStatsComponent implements OnInit {

  statsDataSource = new MatTableDataSource<UserStats>();
  statsDisplayedColumns: string[]
      = ['plannings', 'issues', 'votes', 'firstVotesBelowEstimate', 'firstVotesAboveEstimate',
    'firstVotesEqualEstimate', 'averageFirstVoteLevelDifference'];

  constructor(private userStatsService: UserStatsService) {
  }

  ngOnInit() {
    this.userStatsService.getMyUserStats().subscribe((response: UserStats) => {
      this.statsDataSource.data = [response];
    });
  }
}
