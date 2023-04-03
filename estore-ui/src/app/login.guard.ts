import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from './services/user.service';

@Injectable({
    providedIn: 'root'
  })
  export class LoginGuard implements CanActivate {
  
    constructor(private userService: UserService, private router: Router) {}
  
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const isLoggedIn = this.userService.isLoggedIn();
      if (isLoggedIn) {
        return true;
      } else {
        // redirect the user to the login page
        this.router.navigate(['/login']);
        return false;
      }
    }
  
  }
  