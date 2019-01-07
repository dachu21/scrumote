import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const apiPrefix = '/api';
    if (!request.url.includes('assets')
        && request.url !== '/logout') {
      request = request.clone({
        url: apiPrefix + request.url
      });
    }
    return next.handle(request);
  }
}
