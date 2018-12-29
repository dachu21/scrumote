import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class AlertService {

  constructor(private snackBar: MatSnackBar, private translate: TranslateService) {
  }

  success(i18nCode: string) {
    this.translate.get(i18nCode).subscribe((message: string) => {
      this.snackBar.open(message, 'Close', {
        duration: 10000
      });
    });
  }

  error(i18nCode: string) {
    this.translate.get(i18nCode).subscribe((message: string) => {
      this.snackBar.open(message, 'Close', {
        duration: 10000
      });
    });
  }
}
