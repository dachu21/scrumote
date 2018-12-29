import {Routes} from '@angular/router';
import {HomeComponent, LoginComponent, RegisterComponent} from './_components';
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
      authenticated: true,
      fallbackUrl: '/login'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false,
      fallbackUrl: '/'
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [AuthenticationGuard],
    data: {
      authenticated: false,
      fallbackUrl: '/'
    }
  }
];
