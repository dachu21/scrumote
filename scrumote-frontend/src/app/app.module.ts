import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {FlexLayoutModule} from '@angular/flex-layout';

import {AppComponent} from './app.component';
import {
  EditPlanningComponent,
  ErrorComponent,
  HomeComponent,
  LoginComponent,
  PlanningListComponent,
  RegisterComponent,
  UserListComponent
} from './_components';

import {CustomMaterialModule} from './_modules';
import {ApiInterceptor, ErrorInterceptor, XhrInterceptor} from './_interceptors';
import {AuthenticationGuard, AuthorizationGuard} from './_guards';
import {
  AlertService,
  AuthenticationService,
  DeckService,
  IssueService,
  PlanningService,
  SystemFeatureService,
  UserService,
  UserStatsService,
  VoteService
} from './_services';

import {ROUTES as routes} from './app.routes';
import {httpLoaderFactory} from './_functions';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ErrorComponent,
    PlanningListComponent,
    EditPlanningComponent,
    UserListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    CustomMaterialModule,
    RouterModule.forRoot(routes),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: httpLoaderFactory,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ApiInterceptor, multi: true},
    AuthorizationGuard,
    AuthenticationGuard,
    AuthenticationService,
    AlertService,
    UserService,
    PlanningService,
    DeckService,
    IssueService,
    SystemFeatureService,
    UserStatsService,
    VoteService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
