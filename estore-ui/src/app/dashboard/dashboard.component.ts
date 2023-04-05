import { Component, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { Product } from '../product';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent{
  name: string | undefined = "";
  topCategory: string = "";
  categoryNum: number = 0;
  constructor(public userService: UserService) {}

  ngOnInit(): void {
    this.createRecommended();
  }

  async createRecommended(): Promise<void> {
    var orders = this.userService.getCurrentUser()?.orders;
    var orderIndex = 0;
    var products: Product[] = [];
    var categoryMap: Record<string, number> = {};
    if(orders !== undefined){
      while (orderIndex < orders.length){
        products = orders[orderIndex].products;
        var productIndex = 0;
        while (productIndex < products.length){
          if(products[productIndex].category in categoryMap){
            categoryMap[products[productIndex].category] += 1;
          } else{
            categoryMap[products[productIndex].category] = 1;
          }
          if(categoryMap[products[productIndex].category] > this.categoryNum){
            this.topCategory = products[productIndex].category;
            this.categoryNum = categoryMap[products[productIndex].category];
          }
          productIndex += 1;
        }
        orderIndex += 1;
      }
    }
    var starredIndex = 0;
    var user = await this.userService.getCurrentUser();
    if(user !== null && user !== undefined){
      while (starredIndex < user.starred.length){
        if(user.starred[starredIndex].category in categoryMap){
          categoryMap[user.starred[starredIndex].category] += 1;
        } else{
          categoryMap[user.starred[starredIndex].category] = 1;
        }
        if(categoryMap[user.starred[starredIndex].category] > this.categoryNum){
          this.topCategory = user.starred[starredIndex].category;
          this.categoryNum = categoryMap[user.starred[starredIndex].category];
        }
        starredIndex += 1;
      }
    }
  }
}
