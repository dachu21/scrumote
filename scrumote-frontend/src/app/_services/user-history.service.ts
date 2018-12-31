import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserHistory} from '../_models';

@Injectable()
export class UserHistoryService {

  private usersBaseUrl = '/users/';
  private baseUrl = '/history';

  constructor(private http: HttpClient) {
  }

  getMyUserHistory() {
    return this.http.get<UserHistory>(this.usersBaseUrl + '/my' + this.baseUrl);
  }

  getAnyUserHistory(userId: number) {
    return this.http.get<UserHistory>(this.usersBaseUrl + userId + this.baseUrl);
  }
}
