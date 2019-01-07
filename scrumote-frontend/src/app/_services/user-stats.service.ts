import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserStats} from '../_models';

@Injectable()
export class UserStatsService {

  private usersBaseUrl = '/users/';
  private baseUrl = '/stats';

  constructor(private http: HttpClient) {
  }

  getMyUserStats() {
    return this.http.get<UserStats>(this.usersBaseUrl + 'my' + this.baseUrl);
  }

  getAnyUserStats(userId: number) {
    return this.http.get<UserStats>(this.usersBaseUrl + userId + this.baseUrl);
  }
}
