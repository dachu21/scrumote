import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {
  AllUsersVotedEvent,
  IssueActivatedEvent,
  IssueCreatedEvent,
  IssueDeletedEvent,
  IssueEstimatedEvent,
  IssueUpdatedEvent,
  PlanningCreatedEvent,
  PlanningDeletedEvent,
  PlanningFinishedEvent,
  PlanningUpdatedEvent
} from '../_events';

@Injectable()
export class NotificationsService {

  private readonly URL = '/notifications';
  private eventSource?: EventSource;

  allUsersVotedEvent = new Subject<AllUsersVotedEvent>();
  issueActivatedEvent = new Subject<IssueActivatedEvent>();
  issueEstimatedEvent = new Subject<IssueEstimatedEvent>();
  issueUpdatedEvent = new Subject<IssueUpdatedEvent>();
  issueDeletedEvent = new Subject<IssueDeletedEvent>();
  issueCreatedEvent = new Subject<IssueCreatedEvent>();

  planningFinishedEvent = new Subject<PlanningFinishedEvent>();
  planningCreatedEvent = new Subject<PlanningCreatedEvent>();
  planningUpdatedEvent = new Subject<PlanningUpdatedEvent>();
  planningDeletedEvent = new Subject<PlanningDeletedEvent>();

  constructor() {
  }

  connect() {
    this.eventSource = new EventSource(this.URL);

    this.eventSource.addEventListener('open', message => {
      console.log('SSE connection opened!');
    });
    this.eventSource.addEventListener('error', message => {
      console.log('SSE connection lost!');
    });

    this.addEventListeners();
  }

  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
    }
  }

  private addEventListeners() {
    if (this.eventSource) {
      this.eventSource.addEventListener('allUsersVoted', message => {
        const event = JSON.parse((<any>message).data);
        this.allUsersVotedEvent.next(event);
      });
      this.eventSource.addEventListener('issueActivated', message => {
        const event = JSON.parse((<any>message).data);
        this.issueActivatedEvent.next(event);
      });
      this.eventSource.addEventListener('issueEstimated', message => {
        const event = JSON.parse((<any>message).data);
        this.issueEstimatedEvent.next(event);
      });
      this.eventSource.addEventListener('issueUpdated', message => {
        const event = JSON.parse((<any>message).data);
        this.issueUpdatedEvent.next(event);
      });
      this.eventSource.addEventListener('issueDeleted', message => {
        const event = JSON.parse((<any>message).data);
        this.issueDeletedEvent.next(event);
      });
      this.eventSource.addEventListener('issueCreated', message => {
        const event = JSON.parse((<any>message).data);
        this.issueCreatedEvent.next(event);
      });

      this.eventSource.addEventListener('planningFinished', message => {
        const event = JSON.parse((<any>message).data);
        this.planningFinishedEvent.next(event);
      });
      this.eventSource.addEventListener('planningCreated', message => {
        const event = JSON.parse((<any>message).data);
        this.planningCreatedEvent.next(event);
      });
      this.eventSource.addEventListener('planningUpdated', message => {
        const event = JSON.parse((<any>message).data);
        this.planningUpdatedEvent.next(event);
      });
      this.eventSource.addEventListener('planningDeleted', message => {
        const event = JSON.parse((<any>message).data);
        this.planningDeletedEvent.next(event);
      });
    }
  }
}
