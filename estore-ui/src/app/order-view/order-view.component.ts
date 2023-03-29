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
  displayedColumns: string[] = ['id', 'name', 'products', 'complete', 'delete'];
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

  isCompletes(orders: Order[]): number{
    //orders for some reason is 0
    let a = 0;
    if(orders.length > 0){
      return 100;
    }else{
     for(let i = 0; i < 20; i++){
        if(this.isComplete(orders[i])){
          a+=1;
         }
      }
      a = this.total;
    return a;
    }
    // this.total = this.orders.length;
    // let t = this.total;
    // this.total = this.total/this.orders.length;
    // this.total;
    
  }
}