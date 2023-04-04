import { Component, OnInit} from '@angular/core';
import { UserService } from '../services/user.service';
import { Product } from '../product';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent{
  name: string | undefined = ""
  constructor(public userService: UserService) {}

  createRecommended(): void {
    var orders = this.userService.getCurrentUser()?.orders;
    var orderIndex = 0;
    var products: Product[] = [];
    if(orders !== undefined){
      while (orderIndex < orders.length){
        products = orders[orderIndex].products;
        var productIndex = 0;
        while (productIndex < products.length){
          products[productIndex] //.category
          productIndex += 1;
        }
        orderIndex += 1;
      }
    }
  }
}
