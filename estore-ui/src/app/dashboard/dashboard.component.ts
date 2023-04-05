import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Product } from '../product';
import { ProductService } from '../services/product.service';
import { CategoryService } from '../services/category.service'
import { firstValueFrom } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  productCopies: Product[] = [];
  starred: Product[] | undefined = [];
  products: Product[] = [];
  constructor(
    public userService: UserService,
    public categoryService: CategoryService,
    public productService: ProductService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    this.getProducts();
    this.createRecommended();
    this.starred = this.userService.getCurrentUser()?.starred;
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  async createRecommended(): Promise<void> {
    // Get current user
    let customer = this.userService.getCurrentUser();

    // Just display welcome page if it is null
    if (customer == null) {
      return;
    }

    // Get a list of the categories of all starred products
    let categories: string[] = [];
    for (let product of customer.starred) {
      // Get the category of the product
      let category = (await firstValueFrom(this.productService.getProduct(product.id))).category;

      // Now add this category to our set
      // Only add if it isn't already in the set
      if (!categories.includes(category)) {
        categories.push(category);
      }

    }

    // If we have no categories then exit
    if (categories.length == 0) {
      return;
    }

    // Okay now we have a list of these categories. Randomly select three
    let selectedCategories: string[] = [];
    for (let category of categories) {
      // Randomly select a category
      let random = Math.floor(Math.random() * categories.length);
      if (random < 3 && !selectedCategories.includes(category)) {
        selectedCategories.push(category);
      }
    }

    // If none were added then we will add the first one
    if (selectedCategories.length == 0) {
      selectedCategories.push(categories[0]);
    }

    // Now we have a list of categories, we want random products in those categories
    let selectedProducts: number[] = [];
    for (let category of selectedCategories) {
      // Get a list of products in this category
      let products = await firstValueFrom(this.categoryService.getProductsByCategory(category.toString()));

      // Now take two products randomly
      for (let product of products) {
        let random = Math.floor(Math.random() * products.length);
        if (random < 2 && !selectedProducts.includes(product.id)) {
          selectedProducts.push(product.id);
        }
      }

      // If none were added then we will add the first one
      if (selectedProducts.length == 0 && products.length > 0) {
        selectedProducts.push(products[0].id);
      }
    }

    // Now we should have a list of products to display
    // Load them into product copies
    for (let productId of selectedProducts) {
      let product = await firstValueFrom(this.productService.getProduct(productId));
      this.productCopies.push(product);
    }
  }

  unstar(product: Product): void {
    if (this.starred !== null && this.starred !== undefined) {
      const index = this.starred.indexOf(product, 0);
      if (index > -1) {
        this.starred.splice(index, 1);
      }
    }
  }

  isStarred(product: Product): boolean {
    if (this.starred !== undefined) {
        if (this.starred.find(starred => starred.id === product.id)) {
          return true;
        }
    }

    return false;
  }


}
