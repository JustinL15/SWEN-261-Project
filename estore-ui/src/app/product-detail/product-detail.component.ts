import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';
import { Cart } from '../cart';
import { CartService } from '../services/cart.service';
import { Customer } from '../customer';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})

export class ProductDetailComponent implements OnInit {
  product: Product | undefined;
  user: Customer | null | undefined;
  quantity: number = 1;
  errorMessage = "";

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private userService: UserService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getProduct();
    this.user = this.userService.getCurrentUser();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  addToCart(): void {
    var id: number | undefined = this.product?.id;
    if(this.user !== null && id !== undefined && this.user !== undefined) {
      if(this.user.cartId === 0) {
        this.cartService.createCart({} as Cart).subscribe(cart => {
          if(this.user !== null && id !== undefined && this.user !== undefined) {
          this.userService.updateCustomer({id: this.user.id, username: this.user.username,
          name: this.user.name, cartId: cart.id, orders: this.user.orders, starred: this.user.starred,
          isAdmin: this.user.isAdmin, password: this.user.password}).subscribe(user => {
            if(id !== undefined) {
              this.addProduct(user.cartId, id);
            }
          });
          this.userService.updateCartId(cart.id);
        }
      });
      } else {
        this.addProduct(this.user.cartId, id);
      }
    }
  }

  addProduct(cartId: number, pid: number): void {
    this.errorMessage = "";
    let inCart = 0;
    this.cartService.getCart(cartId).subscribe(cart => {
      var prod = Object.values(cart.inventory).find(reference => reference.id === pid);
      if(prod !== undefined) {
        inCart = prod.quantity;
      }
      if(this.product !== undefined) {
        this.quantity = + this.quantity;
        if(this.product?.quantity >= (this.quantity + + inCart)) {
          this.cartService.addToCart(cartId, {id: pid, quantity: this.quantity}).subscribe();
        } else {
          this.errorMessage = "Only " + this.product?.quantity + " item(s) are in stock.";
          window.alert(this.errorMessage);
        }
      }
    })
  }

  goBack(): void {
    this.location.back();
  }
}
