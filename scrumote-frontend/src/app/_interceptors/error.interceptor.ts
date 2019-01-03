import {Injectable} from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AlertService} from '../_services';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private alert: AlertService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(catchError((error: HttpErrorResponse) => {
      if (error.error) {
        const errorCode: string = error.error.code;
        if (errorCode) {
          this.alert.error(errorCode);
        }
      }

      return throwError(error);
    }));
  }
}
