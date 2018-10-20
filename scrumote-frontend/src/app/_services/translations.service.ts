import {Injectable} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Injectable()
export class TranslationsService {

  constructor(
      private translateService: TranslateService) {
  }

  get errorMessageResources() {
    return this.translateService.get("errorMessageResources");
  }

  get labels() {
    return this.translateService.get("labels");
  }
}
