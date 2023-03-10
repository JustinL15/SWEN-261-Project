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
  isRegistering: boolean = false;
  loginError: boolean = false;
  registerError: boolean = false;
  username: string = ""
  password: string = ""
  name: string = ""


  constructor(public userService: UserService) {}

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers(): void {
    this.userService.getCustomers()
    .subscribe(customers => this.customers = customers);
  }

  login(): void {
    this.loginError = false;
    this.userService.login(this.username, this.password).subscribe();
    if(!this.userService.isLoggedIn()) {
      this.loginError = true;
    }
  }

  logout() {
    this.reset();
    this.userService.logout();
  }
  reset() {
    this.loginError = false;
    this.isRegistering = false;
    this.registerError = false;
  }
  register() {
    if(this.isRegistering) {
      this.userService.register(
        {name: this.name, username: this.username, password: this.password} as Customer).subscribe();
      if(!this.userService.isLoggedIn()) {
      this.registerError = true;
      }
    } else {
      this.isRegistering = true;
      this.username = "";
      this.password = "";
    }

  }
}