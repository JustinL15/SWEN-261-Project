import { Observable, of } from 'rxjs';

/**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   * @param errorHandled - a parameter passed in to make sure the error is handled
   */
export function handleError<T>(operation = 'operation', result?: T, errorHandled = false) {
    return (error: any): Observable<T> => {
      if (error.status === 404) {
        error.statusText = 'NOT FOUND';
      } else if (error.status === 409) {
        error.statusText = 'CONFLICT';
      } else if (error.status === 500) {
        error.statusText = 'INTERNAL SERVER ERROR';
      }
      console.error(`${operation} failed: ${error.message}`);
      if (!errorHandled) {
        // Show error message using appropriate method
        window.alert(`${operation} failed: ${error.status} ${error.statusText}`);
      }
      return of(result as T);
    };
  }