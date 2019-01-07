import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Role} from '../_models';

@Injectable()
export class RoleService {

  private baseUrl = '/roles';

  constructor(private http: HttpClient) {
  }

  getAllRoles() {
    return this.http.get<Role[]>(this.baseUrl);
  }
}
