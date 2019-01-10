import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {MatPaginatorIntl} from '@angular/material';
import {PropagationStopModule} from 'ngx-propagation-stop';

import {AppComponent} from './app.component';
import {
  ActivateUserComponent,
  AreYouSureDialogComponent,
  CreateUserComponent,
  DeckListComponent,
  EditDeckComponent,
  EditIssueComponent,
  EditPasswordComponent,
  EditPlanningComponent,
  EditUserComponent,
  ErrorComponent,
  ForgotPasswordComponent,
  HomeComponent,
  LoginComponent,
  ManageUserComponent,
  MyStatsComponent,
  OpenedPlanningComponent,
  PlanningListComponent,
  ResetPasswordComponent,
  SystemFeatureListComponent,
  UserListComponent,
  VoteDialogComponent
} from './_components';

import {MatPaginatorIntlCustom} from './_other';
import {CustomMaterialModule} from './_modules';
import {PasswordValidator} from './_directives';
import {ApiInterceptor, ErrorInterceptor, XhrInterceptor} from './_interceptors';
import {AuthenticationGuard, AuthorizationGuard, SystemFeatureGuard} from './_guards';
import {
  AlertService,
  AuthenticationService,
  DeckService,
  DialogService,
  IssueService,
  LanguageService,
  NotificationsService,
  PlanningService,
  RoleService,
  SystemFeatureService,
  UserService,
  UserStatsService,
  UserTokenService,
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
    OpenedPlanningComponent,
    EditIssueComponent,
    ManageUserComponent,
    SystemFeatureListComponent,
    VoteDialogComponent,
    ResetPasswordComponent,
    ForgotPasswordComponent,
    AreYouSureDialogComponent,
    PasswordValidator,
    ActivateUserComponent
  ],
  entryComponents: [
    VoteDialogComponent,
    AreYouSureDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    PropagationStopModule,
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
    {provide: MatPaginatorIntl, useClass: MatPaginatorIntlCustom},
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
    RoleService,
    LanguageService,
    NotificationsService,
    DialogService,
    UserTokenService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
