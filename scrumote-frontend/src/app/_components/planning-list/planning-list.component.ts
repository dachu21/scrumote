import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService, AuthenticationService, PlanningService} from '../../_services';
import {Planning} from '../../_models';
import {MatPaginator, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-planning-list',
  templateUrl: 'planning-list.component.html',
  styleUrls: ['./planning-list.component.css']
})
export class PlanningListComponent implements OnInit {

  readonly listType!: string;

  dataSource = new MatTableDataSource<Planning>();

  displayedColumns: string[]
      = ['code', 'name', 'description', 'deckName', 'moderatorUsername', 'finished', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private planningService: PlanningService) {
    if (this.route.snapshot.url[0].path === 'all-plannings') {
      this.listType = 'all';
    } else if (this.route.snapshot.url[0].path === 'my-plannings') {
      this.listType = 'my';
    }
  }

  private refreshDataSource() {
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

  ngOnInit() {
    this.refreshDataSource();
    this.dataSource.paginator = this.paginator;
  }

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
      this.refreshDataSource();
      this.alert.success('plannings.success.delete');
    });
  }
}
