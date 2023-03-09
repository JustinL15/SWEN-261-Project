import { Component, OnInit } from '@angular/core';

import { Order } from '../order';
import { OrderService } from '../order-service';

@Component({
  selector: 'app-order-view',
  templateUrl: './order-view.component.html',
  styleUrls: ['./order-view.component.css']
})
export class OrderViewComponent implements OnInit {
  orders: Order[] = [];
  comp: boolean = false;
  toggle: boolean = false;

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.orderService.getOrders()
    .subscribe(orders => this.orders = orders);
  }

  delete(order: Order): void {
    this.orders = this.orders.filter(h => h !== order);
    this.orderService.deleteOrder(order.id).subscribe();
  }

  completeOrder(order: Order): void{
    order.complete = true;
    if(order){
      this.orderService.updateOrder(order).subscribe(order => this.orders[order.id-1] = order);
    }
  }

  isComplete(order: Order): boolean{
    if(order.complete){
      return true;
    }
    return false;
  }
}