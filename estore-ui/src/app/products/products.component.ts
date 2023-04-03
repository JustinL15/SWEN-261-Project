import { Component, NgModule, OnInit} from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { Observable, Subject } from 'rxjs';
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CategoryService } from '../services/category.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit{
  products: Product[] = [];
  categories: string[] = [];
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>(); 
  selectedCategory: string = "";
  categoryProducts: Product[] = [];

  constructor(private productService: ProductService,
              private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.getProducts();
    this.products$ = this.searchTerms.pipe(

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.searchProducts(term)),
    );

    this.getCategories();
    this.getSelectedCategories();
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  getCategories(): void {
    this.categoryService.getCategories()
    .subscribe(categories => this.categories = categories);
  }

  getSelectedCategories(): void {
    this.categoryService.getProductsByCategory(this.selectedCategory)
    .subscribe(categoryProducts => this.categoryProducts = categoryProducts);
  }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
}
