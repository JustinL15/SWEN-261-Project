import { Component, OnInit } from '@angular/core';
import { DeleteConfirmComponent } from '../delete-confirm/delete-confirm.component';
import { OrderService } from '../services/order-service';
import { Customer } from '../customer';
import { UserService } from '../services/user.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Order } from '../order';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent implements OnInit{
  user: Customer | null = null;
  orders: Order[] = [];
  displayedColumns: string[] = ['id', 'name', 'products', 'total', 'time'];

  constructor(
    private userService: UserService,
    private orderService: OrderService,
    private router: Router,
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.getUser();
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
    const orderObservables = this.user?.orders.map(id => this.orderService.getOrder(id)) || [];
    forkJoin(orderObservables).subscribe((orders: Order[]) => {
      this.orders = orders.filter(order => order !== undefined); 
      this.orders;
    });
  }

  getDate(order: Order): string {
    return (`${order.time[1]}/${order.time[2]}/${order.time[0]} `)
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

  deleteUser() {
    let confirm = this.matDialog.open(DeleteConfirmComponent);
    confirm.afterClosed().subscribe(reply => {
      if(reply === "true") {
        if(this.user !== null) {
          this.userService.deleteCustomer(this.user.id).subscribe(_ => {
            this.logout();
          });
        }
      }
    })
  }
  
}