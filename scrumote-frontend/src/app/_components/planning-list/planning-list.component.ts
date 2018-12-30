import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {PlanningService} from '../../_services';
import {Planning} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-planning-list',
  templateUrl: 'planning-list.component.html'
})
export class PlanningListComponent implements OnInit {

  displayedColumns: string[]
      = ['code', 'name', 'description', 'deckName', 'moderatorUsername', 'finished'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  dataSource = new MatTableDataSource<Planning>();

  constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private planningService: PlanningService,
      private route: ActivatedRoute) {
  }

  ngOnInit() {
    if (this.route.snapshot.url[0].path === 'all-plannings') {
      this.planningService.getAllPlannings().subscribe((response: Planning[]) => {
        this.dataSource.data = response;
      });
    } else if (this.route.snapshot.url[0].path === 'my-plannings') {
      this.planningService.getMyPlannings().subscribe((response: Planning[]) => {
        this.dataSource.data = response;
      });
    }
    this.dataSource.paginator = this.paginator;
  }

}
