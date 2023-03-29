import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {
  private _errorCode: number | null = null;

  get errorCode(): number | null {
    return this._errorCode;
  }

  clearErrorCode(): void {
    this._errorCode = null;
  }

  /**
  * Handle Http operation that failed.
  * Let the app continue.
  *
  * @param operation - name of the operation that failed
  * @param result - optional value to return as the observable result
  */
  handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      if (error.status === 404) {
        error.statusText = 'NOT FOUND';
      } else if (error.status === 409) {
        error.statusText = 'CONFLICT';
      } else if (error.status === 500) {
        error.statusText = 'INTERNAL SERVER ERROR';
      }
      error.message = `${operation} failed: ${error.status} ${error.statusText}`
      console.error(`${operation} failed: ${error.message}`);
      this._errorCode = error.status;
      return of(result as T);
    };
  }
}