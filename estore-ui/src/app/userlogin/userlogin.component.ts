import { Component, OnInit} from '@angular/core';
import { Customer } from '../customer';
import { UserService } from '../user.service';

@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent implements OnInit{
  customers: Customer[] = [];

  isLoggedIn = this.userService.isLoggedIn();
  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers(): void {
    this.userService.getCustomers()
    .subscribe(customers => this.customers = customers);
  }

  login(username: string, password: string) {
    username = username.trim();
    password = password.trim();
    this.userService.login(username, password);
  }

  logout() {
    this.userService.logout();
  }
}