import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Planning} from '../_models';
import {ifMatchOptions} from '../_functions';

@Injectable()
export class PlanningService {

  private baseUrl = '/plannings';

  planningToOpen?: Planning;
  planningToEdit?: Planning;

  constructor(private http: HttpClient) {
  }

  createPlanning(planning: Planning) {
    return this.http.post(this.baseUrl, planning);
  }

  getPlanning(id: number) {
    return this.http.get(this.baseUrl + '/' + id);
  }

  getAllPlannings() {
    return this.http.get<Planning[]>(this.baseUrl);
  }

  getMyPlannings() {
    return this.http.get<Planning[]>(this.baseUrl + '/my');
  }

  updatePlanning(planning: Planning) {
    return this.http.put(this.baseUrl + '/' + planning.id,
        planning, ifMatchOptions(planning.version));
  }

  finishPlanning(planning: Planning) {
    return this.http.put(this.baseUrl + '/' + planning.id + '/finish',
        null, ifMatchOptions(planning.version));
  }

  deletePlanning(planning: Planning) {
    return this.http.delete(this.baseUrl + '/' + planning.id,
        ifMatchOptions(planning.version));
  }
}
