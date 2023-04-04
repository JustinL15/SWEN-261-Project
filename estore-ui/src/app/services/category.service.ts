import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ProductService } from './product.service';
import { Product } from '../product';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  constructor(
    private productService: ProductService) { }

  /**
   * Get all categories used by products
   */
  getCategories(): Observable<string[]> {
    // Now we loop through and keep track of all categories
    let categories: string[] = [];
    
    this.productService.getProducts().subscribe(products => { 
      products.forEach(product => {
        // Now we have to loop through each category in each product
          if (!categories.includes(product.category)) {
            categories.push(product.category);
          }
      });
    });

    // Now we can return our list of categories
    return of(categories);
  }

  /**
   * Find all product IDs that match a category
   * @param category Category to find from
   */
  getProductsByCategory(category: string): Observable<Product[]> {
    let matchingIds: Product[] = [];
    // Grab all products
    this.productService.getProducts().subscribe(products => {
      // Now loop and see if there is a match
      products.forEach(product => {
        if (product.category === category) {
          matchingIds.push(product);
        }
      });
    });

    return of(matchingIds);
  }
}
