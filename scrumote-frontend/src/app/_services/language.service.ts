import {Injectable} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {AlertService} from './alert.service';

@Injectable()
export class LanguageService {

  private default = 'en';
  private available = ['en', 'pl'];
  private localStorageName = 'language';

  constructor(private translate: TranslateService,
              private alert: AlertService) {
  }

  init() {
    this.translate.setDefaultLang(this.default);
    this.setCurrentLanguage();
  }

  private setCurrentLanguage() {
    let language = this.getSavedLanguage();
    if (!language) {
      language = this.translate.getBrowserLang();
      if (!this.available.includes(language)) {
        language = this.default;
      }
    }
    this.translate.use(language);
  }

  private getSavedLanguage() {
    if (localStorage) {
      const language = localStorage[this.localStorageName];
      if (language && this.available.includes(language)) {
        return language;
      }
    }
    return null;
  }

  private saveLanguage(language: string) {
    if (localStorage) {
      localStorage[this.localStorageName] = language;
    }
  }

  getCurrentLanguage() {
    return this.translate.currentLang;
  }

  changeLanguage(language: string) {
    if (this.available.includes(language)) {
      this.translate.use(language);
      this.saveLanguage(language);
    } else {
      this.alert.error('app.languageNotSupported');
    }
  }
}
