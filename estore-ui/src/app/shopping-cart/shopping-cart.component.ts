import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  products: Product[] = [];


  constructor(private productService: ProductService) { }


  ngOnInit(): void {
    // change to getproducts in shopping cart
    this.getProducts();
  }


  getProducts(): void {
    // change to getproducts in shopping cart
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  increase(product: Product): void {
    // check items are in inventory
    // add one item of the product to the shopping cart
  }

  decrease(product: Product): void {
    // remove one item of the product from shopping cart
    // if zero items of the product remove it from shopping cart
  }

  remove(product: Product): void {
    // delete product from shopping cart
  }

  checkout(): void {
    // check items are in inventory
    // remove items from inventory
    // remove items from shopping cart
    // create an order
  }

}
