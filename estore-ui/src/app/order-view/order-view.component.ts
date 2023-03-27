import { Component, OnInit } from '@angular/core';

import { Order } from '../order';
import { OrderService } from '../services/order-service';

@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.css']
})
export class OrderViewComponent implements OnInit {
  orders: Order[] = [];
  comp: boolean = false;

  constructor(private orderService: OrderService) { }

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
  delete(order: Order): void {
    this.orders = this.orders.filter(h => h !== order);
    this.orderService.deleteOrder(order.id).subscribe();
  }

  /* Will mark the order as complete */
  completeOrder(order: Order): void{
    order.complete = true;
    if(order){
      this.orderService.updateOrder(order).subscribe(order => this.orders[order].complete = order);
    }
  }

  /* Checks the completion status of an order */
  isComplete(order: Order): boolean{
    if(order.complete){
      return true;
    }
    return false;
  }
}