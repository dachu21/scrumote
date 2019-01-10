import {MatPaginatorIntl} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';

@Injectable()
export class MatPaginatorIntlCustom extends MatPaginatorIntl {

  rangePageLabel!: string;

  constructor(private translate: TranslateService) {
    super();
    this.translate.onLangChange.subscribe(() => {
      this.translateLabels();
    });
    this.translateLabels();
  }

  private translateLabels() {
    this.translate.get('paginator.itemsPerPageLabel').subscribe(value => {
      this.itemsPerPageLabel = value;
      this.nextPageLabel = this.translate.instant('paginator.nextPageLabel');
      this.previousPageLabel = this.translate.instant('paginator.previousPageLabel');
      this.firstPageLabel = this.translate.instant('paginator.firstPageLabel');
      this.lastPageLabel = this.translate.instant('paginator.lastPageLabel');
      this.rangePageLabel = this.translate.instant('paginator.rangePageLabel');
      this.getRangeLabel = this.getRangeLabelFunction;
    });
    this.changes.next();
  }

  private getRangeLabelFunction(page: number, pageSize: number, length: number): string {
    if (length === 0 || pageSize === 0) {
      return '0 ' + this.rangePageLabel + ' ' + length;
    }
    length = Math.max(length, 0);
    const startIndex = page * pageSize;
    const endIndex = startIndex < length ?
        Math.min(startIndex + pageSize, length) : startIndex + pageSize;
    return startIndex + ' - ' + endIndex + ' ' + this.rangePageLabel + ' ' + length;
  }
}
