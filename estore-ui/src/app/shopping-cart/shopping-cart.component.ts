import { Component } from '@angular/core';
import { Cart } from '../cart';
import { CartService } from '../cart.service';
import { ProductReference } from '../product-reference';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  cart: Cart = {id: -1, inventory: {}};

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
    // this.inventory = this.inventory.filter(i => i !== inventory);
    this.cartService.removeProduct(this.cart.id, product.id).subscribe();
  }

  checkout(): void {
    // loop through the current number of products in the cart
    var totalPrice = 0;
    var products = [];

    for (const prodId in this.cart.inventory){
      // get the current number of products in the cart
      var inStock = 0;
      var productId: number =+prodId;
      this.productService.getProduct(productId).subscribe(prod => inStock = prod.quantity);

      // check items are in inventory
      var orderQuantity = 0; // get product quantity
      var inStock = 0; // get inventory quantity
      if(inStock < 1){
        break;
      } else if (inStock < 0/*this.cart.inventory*/){
        orderQuantity = inStock;
        // set inventory quantity to 0
      } else{
        // set inventory quantity to 'inventoryQuantity - orderQuantity'
      }
      totalPrice += orderQuantity * 0; // * get product price
      // create snapshop of each product and quantity in cart
      // add to products array
    }
    // clear shopping cart

    if(products.length >= 1){
      // create an order
    }
  }

}
