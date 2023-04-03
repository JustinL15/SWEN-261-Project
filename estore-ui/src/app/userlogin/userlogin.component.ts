import { Component, OnInit } from '@angular/core';
import { Cart } from '../cart';
import { CartService } from '../services/cart.service';
import { Customer } from '../customer';
import { UserService } from '../services/user.service';
import { ErrorService } from '../services/error.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent implements OnInit{
  user: Customer | null = null;

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getUser();
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
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
  
}