import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit{

  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  add(name: string, prc: string, qty: string, description: string): void {
    name = name.trim();
    var price: number = +prc;
    var quantity: number = +qty;
    description = description.trim();
    if (!name || !description || !quantity || !price) {
      return;
    }
    
    this.productService.addProduct({ name, price, quantity, description } as Product).subscribe(product => { this.products.push(product) });
  }

  delete(product: Product): void {
    this.products = this.products.filter(p => p !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}
