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
import { Customer } from '../customer';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  cart: Cart = {id: -1, inventory: {}}; 
  cartId : number | undefined;
  cartContents: Product[];

  constructor(
    private orderService:OrderService,
    private cartService:CartService, 
    private productService: ProductService,
    private userService: UserService,
    private snackBar: MatSnackBar
    ) {
      this.cartContents = [];
    }


  ngOnInit(): void {
    this.cartId = this.userService.getCurrentUser()?.cartId;
    this.getCart();

  }

  /**
   * Loop through the cart and add all products within it to the product array
   */
  async processCart(): Promise<void> {
    this.cartContents = [];
    for (const productId in this.cart.inventory) {
      let iName = await firstValueFrom(this.productService.getProduct(parseInt(productId)));
      iName.quantity = this.cart.inventory[productId].quantity;
      this.cartContents.push(iName);
    }

  }

  async getCart(): Promise<void> {
    if (this.cartId !== undefined ) {
      this.cart = await firstValueFrom(this.cartService.getCart(this.cartId));
      
      // Now get the names of all the products
      this.processCart();
      
    }
  }

  increase(product: Product): void {
    // get the current number of products in the cart
    var inStock = 0;
    this.productService.getProduct(product.id).subscribe(prod => {
      inStock = prod.quantity
      // add one item of the product to the shopping cart
      if(product.quantity + 1 <= inStock){
        product.quantity += 1;
      }
      else {
        window.alert("Only " + inStock + " item(s) are available");
      }

      // Set the quantity in the cart array for the matching ID
      this.cart.inventory[product.id].quantity = product.quantity;

      // Now update the cartcontents array and increase the given products quantity
      this.processCart();

      this.cartService.updateCart(this.cart).subscribe();
    });
  }

  decrease(product: Product): void {
    // remove one item of the product from shopping cart
    product.quantity -= 1;

    // Set the quantity in cart array
    this.cart.inventory[product.id].quantity = product.quantity;

    // Update the product array
    this.processCart();

    // if zero items of the product remove it from shopping cart
    if(product.quantity <= 0){
      this.remove(product);
    }

    this.cartService.updateCart(this.cart).subscribe();
  }

  remove(product: Product): void {
    if(this.cart){
      delete this.cart.inventory[product.id];
    }
    this.processCart();
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
            window.alert("Only " + inStock + " " + prod.name + "(s) are available");
            return;
            prod.quantity = 0;
          } else{
            prod.quantity -= orderQuantity;
          }
          totalPrice += orderQuantity * productPrice;

          // create snapshop of each product and quantity in cart
          products.push({id: prod.id, name: prod.name, price: prod.price, quantity: orderQuantity, description: prod.description, category: prod.category, ownerRecommended: prod.ownerRecommended} as Product); 

          // This means we could buy it, so now we need to update purchased IDs of customer
          this.userService.getCurrentUser()?.purchasedIds.push(prod.id);

          this.userService.updateCustomer(this.userService.getCurrentUser() as Customer).subscribe(_ =>{
            let success = this.snackBar.open("Checkout Successful", "Close");
            });

        }
        this.productService.updateProduct(prod).subscribe();
      }

    // clear shopping cart
    this.cart.inventory = {};
    this.cartContents = [];
    this.cartService.updateCart(this.cart).subscribe();

    // create an order
    if(products.length >= 1){
      var order: Order = { id: 0, totalPrice: totalPrice, products: products} as Order;
      const observableOrder = this.orderService.addOrder(order);
      // observableOrder.subscribe(order => this.orderService.updateOrder(order).subscribe());
      var user = this.userService.getCurrentUser();
      if(user !== null) {
        user.orders.push((await firstValueFrom(observableOrder)).id);
        console.log(user);
        this.userService.updateCustomer(user).subscribe();
      }
    }
  }
}
}
