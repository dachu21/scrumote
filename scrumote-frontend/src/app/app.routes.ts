import {Routes} from '@angular/router';
import {
  EditPlanningComponent,
  ErrorComponent,
  HomeComponent,
  LoginComponent,
  PlanningListComponent,
  RegisterComponent
} from './_components';
import {AuthenticationGuard, AuthorizationGuard} from './_guards';

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
    path: 'register',
    component: RegisterComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false
    }
  },
  {
    path: 'my-plannings',
    component: PlanningListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthority: 'getMyPlannings'
    }
  },
  {
    path: 'all-plannings',
    component: PlanningListComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthority: 'getAllPlannings'
    }
  },
  {
    path: 'edit-planning',
    component: EditPlanningComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthority: 'updatePlanning'
    }
  },
  {
    path: 'create-planning',
    component: EditPlanningComponent,
    canActivate: [AuthorizationGuard],
    data: {
      requiredAuthority: 'createPlanning'
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
