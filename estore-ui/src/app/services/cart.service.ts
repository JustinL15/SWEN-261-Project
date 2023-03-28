import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from "rxjs";
import { catchError, tap } from 'rxjs/operators';

import { Cart } from "../cart";
import { MessageService } from "./message.service";
import { Order } from "../order";
import { ProductReference } from "../product-reference";
import { ErrorService } from "./error.service";

@Injectable({ providedIn: 'root' })
export class CartService {
    private cartUrl = 'http://localhost:8080/carts';  // URL to web api

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor(
        private http: HttpClient,
        private messageService: MessageService,
        private errorService: ErrorService) { }
    
    /** GET cart from the server */
    getCart(id: number): Observable<Cart> {
        this.errorService.clearErrorCode();
        const url = `${this.cartUrl}/${id}`;

        return this.http.get<Cart>(url)
            .pipe(
            tap(_ => this.log('fetched cart')),
            catchError(this.errorService.handleError<Cart>('getCart'))
        );
    }

      /** POST: add a new product to the server */
    createCart(cart: Cart): Observable<Cart> {
        this.errorService.clearErrorCode();
        return this.http.post<Cart>(this.cartUrl, cart, this.httpOptions).pipe(
        tap((newCart: Cart) => this.log(`created cart w/ id=${newCart.id}`)),
        catchError(this.errorService.handleError<Cart>('createdCart'))
        );
    }

    addToCart(id: number, product: ProductReference) {
        this.errorService.clearErrorCode();
        const url = `${this.cartUrl}/addItem/${id}`;
        return this.http.post<any>(url, product, this.httpOptions)
            .pipe(
                catchError(this.errorService.handleError<any>('addToCart'))
            )
    }

    /** PUT: update the Order on the server */
    updateCart(cart: Cart): Observable<any> {
        this.errorService.clearErrorCode();
        return this.http.put(this.cartUrl, cart, this.httpOptions).pipe(
          tap(_ => this.log(`updated product id=${cart.id}`)),
          catchError(this.errorService.handleError<any>('updateProduct'))
        );
      }


    /** Log a CartService message with the CartService */
    private log(message: string) {
        this.messageService.add(`CartService: ${message}`);
    }
}