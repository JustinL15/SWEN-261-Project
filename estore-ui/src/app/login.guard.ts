import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from './services/user.service';
import { LoginDialogComponent } from './login-dialog/login-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(
    private userService: UserService,
    private matDialog: MatDialog,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const isLoggedIn = this.userService.isLoggedIn();
    if (isLoggedIn) {
      return true;
    } else {
      // opens up a login dialog
      let login = this.matDialog.open(LoginDialogComponent);
      login.afterClosed().subscribe(() => {
        // User logged in successfully, navigate to the current route again to reload the page with the new user data
        window.history.back();
      });
      return false;
    }
  }

}
  