import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {AlertService, AuthenticationService, SystemFeatureService} from '../../_services';
import {SystemFeature} from '../../_models';

@Component({
  selector: 'app-system-feature-list',
  templateUrl: 'system-feature-list.component.html',
  styleUrls: ['./system-feature-list.component.css']
})
export class SystemFeatureListComponent implements OnInit {

  systemFeaturesDataSource = new MatTableDataSource<SystemFeature>();

  displayedColumns: string[]
      = ['code', 'enabled', 'actions'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private alert: AlertService,
              readonly auth: AuthenticationService,
              private systemFeatureService: SystemFeatureService) {
  }

  ngOnInit() {
    this.loadSystemFeatures();
    this.systemFeaturesDataSource.paginator = this.paginator;
  }

  private loadSystemFeatures() {
    this.systemFeatureService.getAllSystemFeatures().subscribe((response: SystemFeature[]) => {
      this.systemFeaturesDataSource.data = response;
    });
  }

  toggleSystemFeature(systemFeature: SystemFeature) {
    systemFeature.enabled = !systemFeature.enabled;
    this.systemFeatureService.updateSystemFeature(systemFeature).subscribe(
        () => {
          this.loadSystemFeatures();
          this.alert.success('systemFeatureList.success');
        });
  }

  refreshPage() {
    this.router.navigate(['/system-features']);
  }
}
