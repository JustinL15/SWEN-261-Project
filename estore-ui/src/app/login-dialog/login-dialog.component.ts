import { Component} from '@angular/core';
import { UserService } from '../services/user.service';
import { ErrorService } from '../services/error.service';
import { MatDialogRef } from '@angular/material/dialog';
import { Customer } from '../customer';


@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent {
  username = "";
  password = "";
  name = "";
  loginMode = true;
  errorMessage = "";

  constructor(
    public userService: UserService,
    private errorService: ErrorService,
    private dialogRef: MatDialogRef<LoginDialogComponent>,
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
      if(this.errorService.errorCode === null) {
        this.dialogRef.close();
      }
    });
  }

  register() {
    if(this.name === "") {
      this.errorMessage = "Name cannot be blank.";
      return;
    }
    if(this.username === "") {
      this.errorMessage = "Username cannot be blank.";
      return;
    }
    if(this.password === "") {
    this.errorMessage = "Password cannot be blank.";
    return;
    }
    this.userService.register(
      {name: this.name, username: this.username, password: this.password} as Customer).subscribe(_ => {
        if(this.errorService.errorCode === 409) {
          this.errorMessage = "Username is already taken."
        }
        if(this.errorService.errorCode === null) {
          this.dialogRef.close();
        }
      });
  }
  toggleMode(): void {
    this.loginMode = !this.loginMode;
    this.username = "";
    this.password = "";
    this.name = "";
    this.errorMessage = "";
  }
}
