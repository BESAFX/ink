import{Injectable}from'@angular/core';
import {CanActivate, Router}from '@angular/router';

import {TokenStorageService}from './storage.service';

@Injectable({
providedIn: 'root',
})
export class AuthGuardService implements CanActivate {

constructor(private tokenStorage: TokenStorageService, private router: Router) {}

  canActivate(): boolean {
    if (!this.isAuthenticated()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

  isAuthenticated(): boolean {
    return this.tokenStorage.getToken() !== null;
  }

}
