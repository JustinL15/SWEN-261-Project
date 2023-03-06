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

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    this.getOrders();
  }

  getOrders(): void {
    this.orderService.getOrders()
    .subscribe(orders => this.orders = orders);
  }

}