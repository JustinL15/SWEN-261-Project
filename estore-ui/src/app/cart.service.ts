import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from "rxjs";
import { catchError, tap } from 'rxjs/operators';

import { Cart } from "./cart";
import { MessageService } from "./message.service";
import { Order } from "./order";

@Injectable({ providedIn: 'root' })
export class CartService {
    private productsUrl = 'http://localhost:8080/cart';  // URL to web api

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(
        private http: HttpClient,
        private messageService: MessageService) { }
    
    /** GET cart from the server */
    getCart(id: number): Observable<Cart> {
        const url = `${this.productsUrl}/${id}`;

        return this.http.get<Cart>(url)
            .pipe(
            tap(_ => this.log('fetched cart')),
            catchError(this.handleError<Cart>('getCart'))
        );
    }

    createOrder(order: Order) {
        return this.http.post<Order>(this.productsUrl, order, this.httpOptions).pipe(
            tap((newProduct: Order) => this.log(`added product w/ id=${newProduct.id}`)),
            catchError(this.handleError<Order>('createOrder'))
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

    /** Log a CartService message with the CartService */
    private log(message: string) {
        this.messageService.add(`CartService: ${message}`);
    }
}