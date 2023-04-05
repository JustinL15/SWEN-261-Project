import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../product.service';
import { UserService } from '../user.service';
import { Cart } from '../cart';
import { CartService } from '../cart.service';
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
              this.cartService.addToCart(user.cartId, {id: id, quantity: this.quantity}).subscribe();
            }
          });
          this.userService.updateCartId(cart.id);
        }
      });
      } else {
        this.cartService.addToCart(this.user.cartId, {id: id, quantity: this.quantity}).subscribe();
      }
    }
  }

  starProduct(): void {
    if(this.user !== null && this.product !== undefined && this.user !== undefined) {
      const found = this.user.starred.some(product => {
        if(this.product !== undefined){
        return product.id === this.product.id
        } else {
        return false;
        }
      })
      if(!found){
        this.user.starred.push(this.product);
        this.userService.updateCustomer(this.user).subscribe(user => this.user = user);
      }
    }
  }

  goBack(): void {
    this.location.back();
  }
}
