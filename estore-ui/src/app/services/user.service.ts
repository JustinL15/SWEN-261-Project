import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Customer } from '../customer';
import { MessageService } from './message.service';
import { ErrorService } from './error.service';
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private customersUrl = 'http://localhost:8080/customers';  // URL to web api
  private currentUser: Customer | null = null;
  private loggedIn = false;
  private observableUser: Observable<Customer> | null = null;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private errorService: ErrorService
    ) { }

  /** GET customers from the server */
  getCustomers(): Observable<Customer[]> {
    this.errorService.clearErrorCode();
    return this.http.get<Customer[]>(this.customersUrl)
      .pipe(
        tap(_ => this.log('fetched customers')),
        catchError(this.errorService.handleError<Customer[]>('getCustomers', []))
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
        catchError(this.errorService.handleError<Customer>(`getCustomer id=${id}`))
      );
  }

  /** GET customers by id. Will 404 if id not found */
  getCustomer(id: number): Observable<Customer> {
    this.errorService.clearErrorCode();
    const url = `${this.customersUrl}/${id}`;
    return this.http.get<Customer>(url).pipe(
      tap(_ => this.log(`fetched customer id=${id}`)),
      catchError(this.errorService.handleError<Customer>(`getCustomer id=${id}`))
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
      catchError(this.errorService.handleError<Customer[]>('searchCustomers', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new customer to the server */
  addCustomer(customer: Customer): Observable<Customer> {
    this.errorService.clearErrorCode();
    return this.http.post<Customer>(this.customersUrl, customer, this.httpOptions).pipe(
      tap((newCustomer: Customer) => this.log(`added customer w/ id=${newCustomer.id}`)),
      catchError(this.errorService.handleError<Customer>('addCustomer'))
    );
  }

  /** DELETE: delete the Customer from the server */
  deleteCustomer(id: number): Observable<Customer> {
    this.errorService.clearErrorCode();
    const url = `${this.customersUrl}/${id}`;

    return this.http.delete<Customer>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted customer id=${id}`)),
      catchError(this.errorService.handleError<Customer>('deleteCustomer'))
    );
  }

  /** PUT: update the Customer on the server */
  updateCustomer(customer: Customer): Observable<any> {
    this.errorService.clearErrorCode();
    return this.http.put(this.customersUrl, customer, this.httpOptions).pipe(
      tap(_ => {
        this.log(`updated customer id=${customer.id}`)
        this.currentUser = customer;
      }),
      catchError(this.errorService.handleError<any>('updateCustomer'))
    );
  }

  //////// login methods //////////

  /** POST: Login with credentials specified by customer object you pass in*/
  login(username: string, password: string): Observable<Customer> {
    this.errorService.clearErrorCode();
    const customer = { username, password } as Customer;
    return this.observableUser = this.http.post<Customer>(`${this.customersUrl}/auth`, customer, this.httpOptions)
      .pipe(
        tap((customer: Customer) => {
          if (customer) {
            this.currentUser = customer;
            this.loggedIn = true;
          }
        }),
        catchError(this.errorService.handleError<any>('login'))
      );
  }

  /** update the cart id*/
  updateCartId(id: number) {
    if(this.currentUser !== null) {
      this.currentUser = {id: this.currentUser.id, username: this.currentUser.username,
        name: this.currentUser.name, cartId: id, orders: this.currentUser.orders,
        isAdmin: this.currentUser.isAdmin, starred: this.currentUser.starred, password: this.currentUser.password, purchasedIds: this.currentUser.purchasedIds};
    }
  }


/** POST: register a new customer to the server and login*/
register(customer: Customer): Observable<Customer> {
  this.errorService.clearErrorCode();
  return this.observableUser = this.http.post<Customer>(this.customersUrl, customer, this.httpOptions).pipe(
    tap((newCustomer: Customer) => {
      this.log(`added customer w/ id=${newCustomer.id}`);
      this.currentUser = newCustomer;
      this.loggedIn = true;
    }),
    catchError(this.errorService.handleError<Customer>('Register'))
  );
}

  logout(): void {
    this.currentUser = null;
    this.observableUser = null;
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  getCurrentUser(): Customer | null {
    return this.currentUser;
  }

  getObservableUser(): Observable<Customer> | null {
    return this.observableUser;
  }

  /** Log a CustomerService message with the CustomerService */
  private log(message: string) {
    this.messageService.add(`CustomerService: ${message}`);
  }
  
}
