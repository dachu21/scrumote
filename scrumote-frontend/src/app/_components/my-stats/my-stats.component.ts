import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {UserStatsService} from '../../_services';
import {UserStats} from '../../_models';

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
