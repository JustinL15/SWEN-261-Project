import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


import { Review } from '../review';
import { MessageService } from './message.service';




@Injectable({ providedIn: 'root' })
export class ReviewService {


  private reviewsUrl = 'http://localhost:8080/reviews';  // URL to web api


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }


  /** GET reviews from the server */
  getReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(this.reviewsUrl)
      .pipe(
        tap(_ => this.log('fetched reviews')),
        catchError(this.handleError<Review[]>('getReviews', []))
      );
  }


  /** GET review by id. Return `undefined` when id not found */
  getReviewNo404<Data>(id: number): Observable<Review> {
    const url = `${this.reviewsUrl}/?id=${id}`;
    return this.http.get<Review[]>(url)
      .pipe(
        map(review => review[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} review id=${id}`);
        }),
        catchError(this.handleError<Review>(`getReview id=${id}`))
      );
  }


  /** GET review by id. Will 404 if id not found */
  getReview(id: number): Observable<Review> {
    const url = `${this.reviewsUrl}/${id}`;
    return this.http.get<Review>(url).pipe(
      tap(_ => this.log(`fetched review id=${id}`)),
      catchError(this.handleError<Review>(`getReview id=${id}`))
    );
  }


  //////// Save methods //////////


  /** POST: add a new review to the server */
  addReview(review: Review): Observable<Review> {
    return this.http.post<Review>(this.reviewsUrl, review, this.httpOptions).pipe(
      tap((newReview: Review) => this.log(`added review w/ id=${newReview.id}`)),
      catchError(this.handleError<Review>('addReview'))
    );
  }


  /** DELETE: delete the review from the server */
  deleteReview(id: number): Observable<Review> {
    const url = `${this.reviewsUrl}/${id}`;


    return this.http.delete<Review>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted review id=${id}`)),
      catchError(this.handleError<Review>('deleteReview'))
    );
  }


    /** PUT: update the review on the server */
    updateReview(review: Review): Observable<any> {
      return this.http.put(this.reviewsUrl, review, this.httpOptions).pipe(
        tap(_ => this.log(`updated review id=${review.id}`)),
        catchError(this.handleError<any>('updateReview'))
      );
    }


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {


      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead


      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);


      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }


  /** Log a ReviewService message with the ReviewService */
  private log(message: string) {
    this.messageService.add(`ReviewService: ${message}`);
  }
}

