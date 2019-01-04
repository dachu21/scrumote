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
  CreateUserComponent,
  DeckListComponent,
  EditDeckComponent,
  EditIssueComponent,
  EditPasswordComponent,
  EditPlanningComponent,
  EditUserComponent,
  ErrorComponent,
  HomeComponent,
  LoginComponent,
  MyStatsComponent,
  PlanningComponent,
  PlanningListComponent,
  UserListComponent
} from './_components';

import {CustomMaterialModule} from './_modules';
import {ApiInterceptor, ErrorInterceptor, XhrInterceptor} from './_interceptors';
import {AuthenticationGuard, AuthorizationGuard, SystemFeatureGuard} from './_guards';
import {
  AlertService,
  AuthenticationService,
  DeckService,
  IssueService,
  LanguageService,
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
    CreateUserComponent,
    ErrorComponent,
    PlanningListComponent,
    EditPlanningComponent,
    UserListComponent,
    EditUserComponent,
    EditPasswordComponent,
    MyStatsComponent,
    DeckListComponent,
    EditDeckComponent,
    PlanningComponent,
    EditIssueComponent
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
    SystemFeatureGuard,
    AuthenticationService,
    AlertService,
    UserService,
    PlanningService,
    DeckService,
    IssueService,
    SystemFeatureService,
    UserStatsService,
    VoteService,
    LanguageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
