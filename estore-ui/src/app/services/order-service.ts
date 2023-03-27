import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Order } from '../order';
import { MessageService } from './message.service';
import { handleError } from './handle.error';


@Injectable({ providedIn: 'root' })
export class OrderService {

  private ordersUrl = 'http://localhost:8080/orders';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET orders from the server */
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.ordersUrl)
      .pipe(
        tap(_ => this.log('fetched orders')),
        catchError(handleError<Order[]>('getOrders', []))
      );
  }

  /** GET product by id. Return `undefined` when id not found */
  getOrderNo404<Data>(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/?id=${id}`;
    return this.http.get<Order[]>(url)
      .pipe(
        map(order => order[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} order id=${id}`);
        }),
        catchError(handleError<Order>(`getOrder id=${id}`))
      );
  }

  /** GET orders by id. Will 404 if id not found */
  getOrder(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/${id}`;
    return this.http.get<Order>(url).pipe(
      tap(_ => this.log(`fetched order id=${id}`)),
      catchError(handleError<Order>(`getOrder id=${id}`))
    );
  }

  //////// Save methods //////////

  /** POST: add a new order to the server */
  addOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.ordersUrl, order, this.httpOptions).pipe(
      tap((newOrder: Order) => this.log(`added order w/ id=${newOrder.id}`)),
      catchError(handleError<Order>('addOrder'))
    );
  }

  /** DELETE: delete the order from the server */
  deleteOrder(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/${id}`;

    return this.http.delete<Order>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted order id=${id}`)),
      catchError(handleError<Order>('deleteOrder'))
    );
  }

    /** PUT: update the Order on the server */
    updateOrder(order: Order): Observable<any> {
      return this.http.put(this.ordersUrl, order, this.httpOptions).pipe(
        tap(_ => this.log(`updated product id=${order.id}`)),
        catchError(handleError<any>('updateProduct'))
      );
    }

  /** Log a OrderService message with the ProductService */
  private log(message: string) {
    this.messageService.add(`OrderService: ${message}`);
  }
}