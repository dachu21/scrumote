import {Injectable} from '@angular/core';
import {MatSnackBar, MatSnackBarConfig} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class AlertService {

  private readonly successConfig: MatSnackBarConfig;
  private readonly errorConfig: MatSnackBarConfig;
  private readonly eventConfig: MatSnackBarConfig;

  constructor(private snackBar: MatSnackBar,
              private translate: TranslateService) {
    this.successConfig = new MatSnackBarConfig();
    this.successConfig.panelClass = ['success-alert'];
    this.successConfig.duration = 5000;

    this.errorConfig = new MatSnackBarConfig();
    this.errorConfig.panelClass = ['error-alert'];
    this.errorConfig.duration = 5000;

    this.eventConfig = new MatSnackBarConfig();
    this.eventConfig.panelClass = ['event-alert'];
    this.eventConfig.duration = 5000;
  }

  success(i18nCode: string) {
    this.translate.get(i18nCode).subscribe((message: string) => {
      this.snackBar.open(
          message,
          this.translate.instant('common.close'),
          this.successConfig);
    });
  }

  error(i18nCode: string) {
    this.translate.get(i18nCode).subscribe((message: string) => {
      this.snackBar.open(
          message,
          this.translate.instant('common.close'),
          this.errorConfig);
    });
  }

  event(i18nCode: string) {
    this.translate.get(i18nCode).subscribe((message: string) => {
      this.snackBar.open(
          message,
          this.translate.instant('common.close'),
          this.eventConfig);
    });
  }
}
