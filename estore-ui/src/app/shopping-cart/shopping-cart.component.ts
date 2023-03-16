import { Component } from '@angular/core';
import { Cart } from '../cart';
import { CartService } from '../cart.service';
import { Order } from '../order';
import { Product } from '../product';
import { ProductReference } from '../product-reference';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  cart: Cart = {id: -1, inventory: {}}; // | undefined, but unsure how to change rest

  constructor(
    private cartService:CartService, 
    private productService: ProductService
    ) { }


  ngOnInit(): void {
    this.getCart();
  }


  getCart(): void {
    this.cartService.getCart(this.cart.id).subscribe((cart: Cart) => this.cart = cart);
  }

  increase(product: ProductReference): void {
    // get the current number of products in the cart
    var inStock = 0;
    this.productService.getProduct(product.id).subscribe(prod => inStock = prod.quantity);

    // add one item of the product to the shopping cart
    if(product.quantity + 1 <= inStock){
      product.quantity += 1;
    }
  }

  decrease(product: ProductReference): void {
    // remove one item of the product from shopping cart
    product.quantity -= 1;

    // if zero items of the product remove it from shopping cart
    if(product.quantity <= 0){
      this.remove(product);
    }
  }

  remove(product: ProductReference): void {
    delete this.cart.inventory[product.id];
  } 

  checkout(): void {
    // loop through the current number of products in the cart
    var totalPrice = 0;
    var products = [];

    for (const prodId in this.cart.inventory){
      // get the current number of products in the cart
      var inStock = 0;
      var orderQuantity = 0;
      var productPrice = 0;
      var productId: number =+prodId;
      this.productService.getProduct(productId).subscribe(prod => inStock = prod.quantity);
      this.productService.getProduct(productId).subscribe(prod => productPrice = prod.price);

      // check if any items are in inventory
      if(inStock < 1){
        break;
      }

      // check if limited items are in inventory
      var num: number;
      Object.keys(this.cart.inventory).find(key => orderQuantity = this.cart.inventory[(num=+key)].quantity);
      if (inStock < orderQuantity){
        orderQuantity = inStock;
        Object.keys(this.cart.inventory).find(key => this.cart.inventory[(num=+key)].quantity = 0);
      } else{
        Object.keys(this.cart.inventory).find(key => this.cart.inventory[(num=+key)].quantity -= orderQuantity);
      }
      totalPrice += orderQuantity * productPrice;

      // create snapshop of each product and quantity in cart
      var product: Product = {id: -1, name: "", price: -1, quantity: -1, description: ""};
      this.productService.getProduct(productId).subscribe(prod => product = prod);
      products.push(product);
    }

    // clear shopping cart
    this.cart.inventory = {};

    // create an order
    if(products.length >= 1){
      this.cartService.createOrder(
        { id: this.cart.id, totalPrice: totalPrice, products: products} as Order
        );
    }
  }

}
