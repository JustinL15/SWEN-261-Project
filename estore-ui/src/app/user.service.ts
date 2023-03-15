import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { sha256 } from 'js-sha256';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Customer } from './customer';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  private customersUrl = 'http://localhost:8080/customers';  // URL to web api
  private currentUser: Customer | null = null;
  private loggedIn = false;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET customers from the server */
  getCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.customersUrl)
      .pipe(
        tap(_ => this.log('fetched customers')),
        catchError(this.handleError<Customer[]>('getCustomers', []))
      );
  }

  /** GET customer by id. Return `undefined` when id not found */
  getCustomerNo404<Data>(id: number): Observable<Customer> {
    const url = `${this.customersUrl}/?id=${id}`;
    return this.http.get<Customer[]>(url)
      .pipe(
        map(customer => customer[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} customer id=${id}`);
        }),
        catchError(this.handleError<Customer>(`getCustomer id=${id}`))
      );
  }

  /** GET customers by id. Will 404 if id not found */
  getCustomer(id: number): Observable<Customer> {
    const url = `${this.customersUrl}/${id}`;
    return this.http.get<Customer>(url).pipe(
      tap(_ => this.log(`fetched customer id=${id}`)),
      catchError(this.handleError<Customer>(`getCustomer id=${id}`))
    );
  }

  /* GET customers whose name contains search term */
  searchCustomers(term: string): Observable<Customer[]> {
    if (!term.trim()) {
      // if not search term, return empty customer array.
      return of([]);
    }
    return this.http.get<Customer[]>(`${this.customersUrl}/?text=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found customers matching "${term}"`) :
         this.log(`no customers matching "${term}"`)),
      catchError(this.handleError<Customer[]>('searchCustomers', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new customer to the server */
  addCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.customersUrl, customer, this.httpOptions).pipe(
      tap((newCustomer: Customer) => this.log(`added customer w/ id=${newCustomer.id}`)),
      catchError(this.handleError<Customer>('addCustomer'))
    );
  }

  /** DELETE: delete the Customer from the server */
  deleteCustomer(id: number): Observable<Customer> {
    const url = `${this.customersUrl}/${id}`;

    return this.http.delete<Customer>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted customer id=${id}`)),
      catchError(this.handleError<Customer>('deleteCustomer'))
    );
  }

  /** PUT: update the Customer on the server */
  updateCustomer(customer: Customer): Observable<any> {
    return this.http.put(this.customersUrl, customer, this.httpOptions).pipe(
      tap(_ => this.log(`updated customer id=${customer.id}`)),
      catchError(this.handleError<any>('updateCustomer'))
    );
  }

  //////// login methods //////////

  /** POST: Login with credentials specified by customer object you pass in*/
  login(username: string, password: string): Observable<Customer> {
    const customer = { username, password } as Customer;
    return this.http.post<Customer>(`${this.customersUrl}/auth`, customer, this.httpOptions)
      .pipe(
        tap((customer: Customer) => {
          if (customer) {
            this.currentUser = customer;
            this.loggedIn = true;
          }
        }),
        catchError(this.handleError<any>('login'))
      );
  }

/** POST: register a new customer to the server and login*/
register(customer: Customer): Observable<Customer> {
  return this.http.post<Customer>(this.customersUrl, customer, this.httpOptions).pipe(
    tap((newCustomer: Customer) => {
      this.log(`added customer w/ id=${newCustomer.id}`);
      this.currentUser = newCustomer;
      this.loggedIn = true;
    }),
    catchError(this.handleError<Customer>('addCustomer'))
  );
}

  logout(): void {
    this.currentUser = null;
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  getCurrentUser(): Customer | null {
    return this.currentUser;
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

  /** Log a CustomerService message with the CustomerService */
  private log(message: string) {
    this.messageService.add(`CustomerService: ${message}`);
  }
}
