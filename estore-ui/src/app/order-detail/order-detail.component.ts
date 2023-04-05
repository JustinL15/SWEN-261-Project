import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Product } from '../product';
import { UserService } from '../services/user.service';
import { OrderService } from '../services/order-service';
import { Customer } from '../customer';
import { Order } from '../order';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailComponent implements OnInit {
  products: Product[] = [];
  user: Customer | null | undefined;
  order: Order | undefined;
  errorMessage = "";

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private userService: UserService,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.user = this.userService.getCurrentUser();
    this.orderService.getOrder(id).subscribe(order => this.order = order);
  }


  goBack(): void {
    this.location.back();
  }

}
