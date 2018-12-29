import {HttpClient} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {ENVIRONMENT as env} from '../app.environment';

export function httpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, env.i18n, '.json');
}
