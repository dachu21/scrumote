import {HttpHeaders} from '@angular/common/http';

export function ifMatchOptions(version: number | null) {
  if (version === null) {
    return new Object({headers: {}});
  }
  const httpHeaders = new HttpHeaders({
    'If-Match': version.toString()
  });
  return new Object({headers: httpHeaders});
}
