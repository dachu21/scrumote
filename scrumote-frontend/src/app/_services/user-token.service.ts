import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Password, User} from '../_models';
import {LanguageService} from './language.service';
import {ForgotPasswordForm} from '../_interfaces';

@Injectable()
export class UserTokenService {

  private baseUrl = '/user-tokens';

  constructor(private http: HttpClient,
              private language: LanguageService) {
  }


  getUsernameForToken(token: string) {
    return this.http.get<User>(this.baseUrl + '/' + token + '/username');
  }

  createResetPasswordToken(forgotPasswordForm: ForgotPasswordForm) {
    return this.http.post<ForgotPasswordForm>(this.baseUrl + '/reset-password', forgotPasswordForm, {
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
