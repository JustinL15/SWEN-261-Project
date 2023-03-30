import { Component } from '@angular/core';
import { Product } from '../product';
import { UserService } from '../user.service';

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
}
