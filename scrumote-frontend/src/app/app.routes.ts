import {Routes} from '@angular/router';
import {ErrorComponent, HomeComponent, LoginComponent, RegisterComponent} from './_components';
import {AuthenticationGuard} from './_guards';

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
