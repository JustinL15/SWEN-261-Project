import { Component} from '@angular/core';
import { Customer } from '../customer';
import { UserService } from '../user.service';

@Component({
  selector: 'app-userlogin',
  templateUrl: './userlogin.component.html',
  styleUrls: ['./userlogin.component.css']
})
export class UserLoginComponent {
  isLoggedIn = this.userService.isLoggedIn();
  constructor(private userService: UserService) {}

  login(username: string, password: string) {
    username = username.trim();
    password = password.trim();
    this.userService.login(username, password);
  }

  logout() {
    this.userService.logout();
  }
}