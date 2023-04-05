import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Customer } from '../customer';
import { MessageService } from './message.service';
import { ErrorService } from './error.service';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customersUrl = 'http://localhost:8080/customers';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService,
    private errorService: ErrorService) { }


  /** GET customers from the server */
  getCustomers(): Observable<Customer[]> {
    this.errorService.clearErrorCode();
    return this.http.get<Customer[]>(this.customersUrl)
      .pipe(
        tap(_ => this.log('fetched customers')),
        catchError(this.errorService.handleError<Customer[]>('getCustomers', []))
    );
  }

  /** GET customer by id. Return `undefined` when id does not exist */
  getCustomerNo404<Data>(id: number): Observable<Customer> {
    this.errorService.clearErrorCode();
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

  /** POST: add a new customer to backend */
  addCustomer(customer: Customer): Observable<Customer> {
    this.errorService.clearErrorCode();

    return this.http.post<Customer>(this.customersUrl, customer, this.httpOptions).pipe(
      tap((newCustomer: Customer) => this.log(`added customer w/ id=${newCustomer.id}`)),
      catchError(this.errorService.handleError<Customer>('addCustomer'))
    );
  }

  /** DELETE: delete the Customer from backend */
  deleteCustomer(id: number): Observable<Customer> {
    this.errorService.clearErrorCode();
    const url = `${this.customersUrl}/${id}`;

    return this.http.delete<Customer>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted customer id=${id}`)),
      catchError(this.errorService.handleError<Customer>('deleteCustomer'))
    );
  }

  /** PUT: update the Customer on backend */
  updateCustomer(customer: Customer): Observable<any> {
    this.errorService.clearErrorCode();
    
    return this.http.put(this.customersUrl, customer, this.httpOptions).pipe(
      tap(_ => this.log(`updated customer id=${customer.id}`)),
      catchError(this.errorService.handleError<any>('updateCustomer'))
    );
  }

  /** Log a CustomerService message */
  private log(message: string) {
    this.messageService.add(`CustomerService: ${message}`);
  }
}
