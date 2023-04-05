import { Component, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { Product } from '../product';
import { CategoryService } from '../services/category.service'

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
    var user = await this.userService.getCurrentUser();
    if(user !== null && user !== undefined){
      const min = 0;
      const starredMax = user.starred.length - 1;
      const starredInt = Math.floor(Math.random() * (starredMax - min + 1)) + min;
      var category = user.starred[starredInt].category;
      var productsInCategory: Product[] = [];
      this.categoryService.getProductsByCategory(category).subscribe(prods => productsInCategory = prods);
      const prodMax = this.products.length - 1; 
      const prodInt = Math.floor(Math.random() * (prodMax - min + 1)) + min;
      this.products.push(productsInCategory[prodInt])
    }
  }
}
