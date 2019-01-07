import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SystemFeature} from '../_models';
import {ifMatchOptions} from '../_functions';

@Injectable()
export class SystemFeatureService {

  private baseUrl = '/system-features';

  constructor(private http: HttpClient) {
  }

  getSystemFeature(code: string) {
    return this.http.get<SystemFeature>(this.baseUrl + '/' + code);
  }

  getAllSystemFeatures() {
    return this.http.get<SystemFeature[]>(this.baseUrl);
  }

  updateSystemFeature(systemFeature: SystemFeature) {
    return this.http.put<SystemFeature>(this.baseUrl + '/' + systemFeature.id,
        systemFeature, ifMatchOptions(systemFeature.version));
  }
}
