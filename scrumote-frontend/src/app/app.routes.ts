import {Routes} from '@angular/router';
import {
  ActivateUserComponent,
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
} from './_components';
import {AuthenticationGuard, AuthorizationGuard, SystemFeatureGuard} from './_guards';

export const ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'home',
  },
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: true
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'reset-password/:token',
    component: ResetPasswordComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'activate/:token',
    component: ActivateUserComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'register',
    component: CreateUserComponent,
    canActivate: [AuthenticationGuard, SystemFeatureGuard],
    data: {
      authenticated: false,
      systemFeature: 'REGISTRATION'
    }
  },
  {
    path: 'create-user',
    component: CreateUserComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['createUser']
    }
  },
  {
    path: 'my-plannings',
    component: PlanningListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getMyPlannings']
    }
  },
  {
    path: 'all-plannings',
    component: PlanningListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getAllPlannings']
    }
  },
  {
    path: 'edit-planning',
    component: EditPlanningComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updatePlanning']
    }
  },
  {
    path: 'create-planning',
    component: EditPlanningComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['createPlanning']
    }
  },
  {
    path: 'users',
    component: UserListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getAllUsers']
    }
  },
  {
    path: 'edit-any-user',
    component: EditUserComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateAnyUser']
    }
  },
  {
    path: 'edit-my-user',
    component: EditUserComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateMyUser']
    }
  },
  {
    path: 'edit-any-password',
    component: EditPasswordComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateAnyUsersPassword']
    }
  },
  {
    path: 'edit-my-password',
    component: EditPasswordComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateMyUsersPassword']
    }
  },
  {
    path: 'my-stats',
    component: MyStatsComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getMyUserStats']
    }
  },
  {
    path: 'decks',
    component: DeckListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getAllDecks']
    }
  },
  {
    path: 'create-deck',
    component: EditDeckComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['createDeck']
    }
  },
  {
    path: 'edit-deck',
    component: EditDeckComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateDeck']
    }
  },
  {
    path: 'planning',
    component: OpenedPlanningComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getMyPlanning', 'getAnyPlanning'],
      useOr: true
    }
  },
  {
    path: 'create-issue',
    component: EditIssueComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['createIssue'],
    }
  },
  {
    path: 'edit-issue',
    component: EditIssueComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['updateIssue'],
    }
  },
  {
    path: 'manage-any-user',
    component: ManageUserComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['manageAnyUser']
    }
  },
  {
    path: 'system-features',
    component: SystemFeatureListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthorities: ['getAllSystemFeatures']
    }
  },
  {
    path: 'error',
    component: ErrorComponent
  },
  {
    path: '**',
    component: ErrorComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: true,
      error404: true
    }
  }
];
