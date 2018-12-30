import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Planning} from '../_models';

@Injectable()
export class PlanningService {

  constructor(private http: HttpClient) {
  }

  getAllPlannings() {
    return this.http.get<Planning[]>('api/plannings');
  }

  getMyPlannings() {
    return this.http.get<Planning[]>('api/plannings/my');
  }
}
