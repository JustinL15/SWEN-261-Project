import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location, NgStyle } from '@angular/common';
import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { ErrorService } from '../services/error.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit{

  products: Product[] = [];
  errorMessage = "";

  constructor(private productService: ProductService, 
    private errorService: ErrorService) { }

  /* list of products on initalization */
  ngOnInit(): void {
    this.getProducts();
  }

  /* returns the products */
  getProducts(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  /* Adds product to inventory. MUST have all filled or will just return */
  add(name: string, prc: string, qty: string, description: string): void {
    this.errorMessage = "";
    name = name.trim();
    var price: number = +prc;
    var quantity: number = +qty;
    description = description.trim();
    if (!name || !description || !quantity || !price) {
      return;
    }
    
    this.productService.addProduct({ name, price, quantity, description } as Product).subscribe(product => { 
      if(this.errorService.errorCode === 409) {
        this.errorMessage = "A product with that name already exists.";
      } else {
        this.products.push(product);
      } 
    });
  }

  /* Delets a product from the inventory */
  delete(product: Product): void {
    this.errorMessage = "";
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}
