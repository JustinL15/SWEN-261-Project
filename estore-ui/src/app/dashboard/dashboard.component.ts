import { Component } from '@angular/core';
import { UserService } from '../services/user.service';
import { Product } from '../product';
import { CategoryService } from '../services/category.service'
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent{
  products: Product[] = [];
  constructor(
    public userService: UserService,
    public categoryService: CategoryService
    ) {}

  ngOnInit(): void {
    this.createRecommended();
  }

  async createRecommended(): Promise<void> {
    let user = this.userService.getCurrentUser();
    if(user !== null && user !== undefined){
      if(user.starred !== undefined){
        const min = 0;
        const starredMax = user.starred.length - 1;
        const starredInt = Math.floor(Math.random() * (starredMax - min + 1)) + min;
        var category = user.starred[starredInt].category;
        var productsInCategory: Product[] = [];
        this.categoryService.getProductsByCategory(category).subscribe(products => {
        productsInCategory = products;
        const prodMax = productsInCategory.length - 1; 
        const prodInt = Math.floor(Math.random() * (prodMax - min + 1)) + min;
        this.products.push(productsInCategory[prodInt]);
        console.log(prodInt);
        console.log(productsInCategory);
        console.log(productsInCategory[prodInt]);
        console.log(this.products);
       })
        // const prodMax = productsInCategory.length - 1; 
        // const prodInt = Math.floor(Math.random() * (prodMax - min + 1)) + min;
        // this.products.push(productsInCategory[prodInt])

        // this.categoryService.getProductsByCategory(category).subscribe
        // console.log(prodInt);
        // console.log(productsInCategory[prodInt]);
        // console.log(productsInCategory);
        // console.log(this.products);
      }
    }
  }
}
