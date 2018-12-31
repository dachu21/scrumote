import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Issue} from '../_models';
import {ifMatchOptions} from '../_functions';

@Injectable()
export class IssueService {

  private planningBaseUrl = '/plannings/';
  private baseUrl = '/issues';

  constructor(private http: HttpClient) {
  }

  createIssue(planningId: number, issue: Issue) {
    return this.http.post<Issue>(this.planningBaseUrl + planningId + this.baseUrl, issue);
  }

  getIssue(planningId: number, id: number) {
    return this.http.get<Issue>(this.planningBaseUrl + planningId + this.baseUrl + '/' + id);
  }

  getIssuesForPlanning(planningId: number) {
    return this.http.get<Issue[]>(this.planningBaseUrl + planningId + this.baseUrl);
  }

  updateIssue(planningId: number, issue: Issue) {
    return this.http.put<Issue>(this.planningBaseUrl + planningId + this.baseUrl + '/' + issue.id,
        issue, ifMatchOptions(issue.version));
  }

  activateIssue(planningId: number, issue: Issue) {
    return this.http.put<null>(
        this.planningBaseUrl + planningId + this.baseUrl + '/' + issue.id + '/activate',
        null, ifMatchOptions(issue.version));
  }

  estimateIssue(planningId: number, issue: Issue, cardValue: string) {
    return this.http.put<string>(
        this.planningBaseUrl + planningId + this.baseUrl + '/' + issue.id + '/estimate',
        cardValue, ifMatchOptions(issue.version));
  }

  deleteIssue(planningId: number, issue: Issue) {
    return this.http.delete(this.planningBaseUrl + planningId + this.baseUrl + '/' + issue.id,
        ifMatchOptions(issue.version));
  }
}
