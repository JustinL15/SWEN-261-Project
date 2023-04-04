import { Time } from '@angular/common';
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
  displayedColumns: string[] = ['id', 'total', 'products', 'time', 'complete', 'delete'];
  displayedColumnsComp: string[] = ['id', 'name', 'products'];
  comp: boolean = false;
  total: number;

  constructor(private orderService: OrderService) { this.total = 0; }

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

  getDate(order: Order): string{

    return (order.time[1] + '/' + order.time[2] + '/' + order.time[0] + 
    ' - ' + order.time[3] + ':' + order.time[4] + ':' + order.time[5]);
  }
}