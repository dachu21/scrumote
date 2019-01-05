import {Injectable} from '@angular/core';

@Injectable()
export class NotificationsService {

  private readonly URL = '/notifications';
  eventSource?: EventSource;

  constructor() {
  }

  connect() {
    const eventSource = new EventSource(this.URL);

    eventSource.addEventListener('open', message => {
      console.log('Connection opened.');
      this.eventSource = eventSource;
    });
    eventSource.addEventListener('error', message => {
      console.log('Connection lost.');
      this.eventSource = undefined;
    });
  }

  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
    }
    this.eventSource = undefined;
  }
}
