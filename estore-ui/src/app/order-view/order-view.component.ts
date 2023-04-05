import { Time } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { Order } from '../order';
import { firstValueFrom } from 'rxjs';
import { OrderService } from '../services/order-service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.css']
})
export class OrderViewComponent implements OnInit {
  orders: Order[] = [];
  displayedColumns: string[] = ['id', 'total', 'products', 'time', 'complete', 'delete'];
  displayedColumnsComp: string[] = ['id', 'total', 'products', 'time'];
  comp: boolean = false;
  total: number;

  constructor(private orderService: OrderService,
    private userService: UserService) { this.total = 0; }

  /* Displays the orders on initalization */
  ngOnInit(): void {
    this.getOrders();
  }

  /* Gets an order */
  getOrders(): void {
    this.orderService.getOrders()
    .subscribe(orders => this.orders = orders);
  }

  /* Removes an order, can not undo */
  async delete(order: Order): Promise<void> {
    this.orders = this.orders.filter(h => h !== order);
    let id = order.id;
    await firstValueFrom(this.orderService.deleteOrder(id));

    let customers = await firstValueFrom(this.userService.getCustomers());

    for (let i = 0; i < customers.length; i++) {
      if(customers[i].orders.includes(id)){
        let ind = customers[i].orders.indexOf(id);
        customers[i].orders.splice(ind, 1);
        await firstValueFrom(this.userService.updateCustomer(customers[i]));
      }
    }
  }

  /* Will mark the order as complete */
  async completeOrder(order: Order): Promise<void> {
    order.complete = true;
    if(order){
      await firstValueFrom(this.orderService.updateOrder(order));
      this.getOrders();
    }
  }

  /* Checks the completion status of an order */
  isComplete(order: Order): boolean{
    if(order.complete){
      return true;
    }
    return false;
  }

  getDate(order: Order): string{

    return (order.time[1] + '/' + order.time[2] + '/' + order.time[0] + 
    ' - ' + order.time[3] + ':' + order.time[4] + ':' + order.time[5]);
  }
}