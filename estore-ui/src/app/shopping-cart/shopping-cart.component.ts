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

  }

  decrease(product: Product): void {
    
  }

  remove(product: Product): void {
    
  }

}
