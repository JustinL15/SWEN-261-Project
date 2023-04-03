import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Cart } from '../cart';
import { CartService } from '../services/cart.service';
import { Order } from '../order';
import { OrderService } from '../services/order-service';
import { Product } from '../product';
import { ProductReference } from '../product-reference';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  cart: Cart = {id: -1, inventory: {}}; 
  cartId : number | undefined;

  constructor(
    private orderService:OrderService,
    private cartService:CartService, 
    private productService: ProductService,
    private userService: UserService
    ) { }


  ngOnInit(): void {
    this.cartId = this.userService.getCurrentUser()?.cartId;
    this.getCart();
  }


  getCart(): void {
    if (this.cartId !== undefined ) {
    this.cartService.getCart(this.cartId).subscribe((cart: Cart) => this.cart = cart);
    }
  }

  increase(product: ProductReference): void {
    // get the current number of products in the cart
    var inStock = 0;
    this.productService.getProduct(product.id).subscribe(prod => {
      inStock = prod.quantity
      // add one item of the product to the shopping cart
      if(product.quantity + 1 <= inStock){
        product.quantity += 1;
      }
      this.cartService.updateCart(this.cart).subscribe();
    });
  }

  decrease(product: ProductReference): void {
    // remove one item of the product from shopping cart
    product.quantity -= 1;

    // if zero items of the product remove it from shopping cart
    if(product.quantity <= 0){
      this.remove(product);
    }

    this.cartService.updateCart(this.cart).subscribe();
  }

  remove(product: ProductReference): void {
    if(this.cart){
      delete this.cart.inventory[product.id];
    }
    this.cartService.updateCart(this.cart).subscribe();
  } 

  async checkout(): Promise<void> {
    // loop through the current number of products in the cart
    var totalPrice = 0;
    var products: Product[] = [];
    if(this.cart){
      for (const prodId in this.cart.inventory){
        // get the current number of products in the cart
        var inStock = 0;
        var orderQuantity = 0;
        var productPrice = 0;
        var productId: number =+prodId;
        const prod = await firstValueFrom(this.productService.getProduct(productId));
        inStock = prod.quantity
        productPrice = prod.price

        // check if limited items are in inventory
        if(inStock >= 1){
          orderQuantity = this.cart.inventory[prodId].quantity;
          if (inStock < orderQuantity){
            orderQuantity = inStock;
            prod.quantity = 0;
          } else{
            prod.quantity -= orderQuantity;
          }
          totalPrice += orderQuantity * productPrice;

          // create snapshop of each product and quantity in cart
          products.push(prod); 
        }
        this.productService.updateProduct(prod).subscribe();
      }

    // clear shopping cart
    this.cart.inventory = {};
    this.cartService.updateCart(this.cart).subscribe();

    // create an order
    if(products.length >= 1){
      var order: Order = { id: this.cart.id, totalPrice: totalPrice, products: products} as Order;
      this.orderService.addOrder(order).subscribe( order =>
        this.orderService.updateOrder(order).subscribe()
      );
    }
    }
  }
}
