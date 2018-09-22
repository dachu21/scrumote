import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppService} from "../app.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  title = 'scrumote-frontend';
  greeting: any;

  constructor(private appService: AppService, private http: HttpClient) {
    http.get('api/hello').subscribe(data => this.greeting = data);
  }

  authenticated() {
    return this.appService.authenticated;
  }

}