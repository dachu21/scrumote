import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {AllUsersVotedEvent} from '../_interfaces';

@Injectable()
export class NotificationsService {

  private readonly URL = '/notifications';
  private eventSource!: EventSource;

  allUsersVotedEvent = new Subject<AllUsersVotedEvent>();

  constructor() {
  }

  connect() {
    this.eventSource = new EventSource(this.URL);

    this.eventSource.addEventListener('open', message => {
      console.log('Connection opened.');
    });
    this.eventSource.addEventListener('error', message => {
      console.log('Connection lost.');
    });

    this.addEventListeners();
  }

  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
    }
  }

  private addEventListeners() {
    this.eventSource.addEventListener('allUsersVoted', message => {
      console.log('allUsersVoted event received.');
      const event = JSON.parse((<any>message).data);
      this.allUsersVotedEvent.next(event);
    });
  }
}
