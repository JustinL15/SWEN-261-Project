import { Component } from '@angular/core';
import { Cart } from '../cart';
import { CartService } from '../services/cart.service';
import { Customer } from '../customer';
import { UserService } from '../services/user.service';
import { ErrorService } from '../services/error.service';


@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent {
  isRegistering: boolean = false;
  username: string = ""
  password: string = ""
  name: string = ""
  errorMessage: string = ""

  constructor(
    public userService: UserService,
    private errorService: ErrorService
    ) {}


  login(): void {
    if(this.username === "") {
      this.errorMessage = "Username cannot be blank.";
      return;
    }
    if(this.password === "") {
      this.errorMessage = "Password cannot be blank.";
      return;
    }
    this.userService.login(this.username, this.password).subscribe(_ => {
      if(this.errorService.errorCode === 404) {
        this.errorMessage = "Username or Password is incorrect."
      }
    });
  }

  logout() {
    this.reset();
    this.userService.logout();
  }
  reset() {
    this.isRegistering = false;
    this.errorMessage = "";
    this.username = "";
    this.password = "";
  }
  register() {
    if(this.isRegistering) {
      if(this.username === "") {
        this.errorMessage = "Username cannot be blank.";
        return;
      }
      if(this.password === "") {
        this.errorMessage = "Password cannot be blank.";
        return;
      }
      if(this.name === "") {
        this.errorMessage = "Name cannot be blank.";
        return;
      }
      this.userService.register(
        {id: 0, username: this.username, name: this.name, password: this.password, purchasedIds: []} as unknown as Customer).subscribe(_ => {
          if(this.errorService.errorCode === 409) {
            this.errorMessage = "Username is already taken."
          }
        });
    } else {
      this.isRegistering = true;
      this.username = "";
      this.password = "";
      this.errorMessage = "";
    }

  }
}