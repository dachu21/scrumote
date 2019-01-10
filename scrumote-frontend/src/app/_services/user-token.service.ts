import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User} from '../_models';
import {LanguageService} from './language.service';

@Injectable()
export class UserTokenService {

  private baseUrl = '/user-tokens';

  constructor(private http: HttpClient,
              private language: LanguageService) {
  }


  getUsernameForToken(token: string) {
    return this.http.get<User>(this.baseUrl + '/' + token + '/username');
  }

  createResetPasswordToken(email: string) {
    return this.http.post<string>(this.baseUrl + '/reset-password', email, {
      params: {
        language: this.language.getCurrentLanguage()
      }
    });
  }

  resetUserPassword(token: string, password: Password) {
    return this.http.put<Password>(this.baseUrl + '/reset-password/' + token, password);
  }

  activateUser(token: string) {
    return this.http.put(this.baseUrl + '/activation/' + token, {});
  }

}
