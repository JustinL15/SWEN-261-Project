import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from "rxjs";
import { catchError, map, tap } from 'rxjs/operators';

import { Cart } from "./cart";
import { MessageService } from "./message.service";
import { Product } from "./product";

@Injectable({ providedIn: 'root' })
export class CartService {
    private productsUrl = 'http://localhost:8080/carts';  // URL to web api

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

    /** DELETE: remove the product from the cart */
    removeProduct(id: number, prodId: number): Observable<Cart> {
        const url = `${this.productsUrl}/${id} ${prodId}`;

        // don't want to delete the cart, want to delete the product reference
        // likely need to add more before this and use delete on product reference
        return this.http.delete<Cart>(url, this.httpOptions).pipe(
            tap(_ => this.log(`removed product id=${prodId}`)),
            catchError(this.handleError<Cart>('removeProduct'))
        );
    }

    createOrder(totalPrice: number, products: Product[]) {

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

    /** Log a ProductService message with the ProductService */
    private log(message: string) {
        this.messageService.add(`ProductService: ${message}`);
    }
}