import { Component } from '@angular/core';
import { Product } from '../product';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-starred-products',
  templateUrl: './starred-products.component.html',
  styleUrls: ['./starred-products.component.css']
})
export class StarredProductsComponent {
  
  starred: Product[] | undefined = [];

  constructor(
    private userService: UserService
    ) { }

  ngOnInit(): void {
    this.starred = this.userService.getCurrentUser()?.starred;
  }

  unstar(product: Product): void {
    if (this.starred !== null && this.starred !== undefined){
      const index = this.starred.indexOf(product, 0);
      if (index > -1) {
        this.starred.splice(index, 1);
      }
    }
  } 
}
