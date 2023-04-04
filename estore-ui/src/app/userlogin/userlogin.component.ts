import { Component, OnInit } from '@angular/core';
import { Cart } from '../cart';
import { OrderService } from '../services/order-service';
import { Customer } from '../customer';
import { UserService } from '../services/user.service';
import { ErrorService } from '../services/error.service';
import { Router } from '@angular/router';
import { Order } from '../order';

@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent implements OnInit{
  user: Customer | null = null;
  orders: Order[] = [];
  displayedColumns: string[] = ['id', 'name', 'products', 'total'];

  constructor(
    private userService: UserService,
    private orderService: OrderService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getUser();
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
    this.orderService.getOrders()
    .subscribe(orders => this.orders = orders.filter(order => !this.user?.orders.includes(order.id)));
  }


  /* saves the product changes */
  save(): void {
    if (this.user !== null) {
      this.userService.updateCustomer(this.user).subscribe();
    }
  }
  logout() {
    this.userService.logout();
    this.router.navigate(['/home']);
  }
  
}