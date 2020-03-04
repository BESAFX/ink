import{ChangeDetectionStrategy, Component, OnInit}from '@angular/core';

import {AuthService}from '../../_services/custom/auth.service';
import {TokenStorageService} from '../../_services/custom/storage.service';
import { AuthLoginInfo}from '../../_dto/login-info';
import {Router} from '@angular/router';

@Component({
selector: 'ink-login',
templateUrl: './login.component.html',
changeDetection: ChangeDetectionStrategy.OnPush,
styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
form: any = {};
isLoggedIn = false;
isLoginFailed = false;
errorMessage = '';
username: string;
roles: string[] = [];
private loginInfo: AuthLoginInfo;

constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private router: Router) { }

  ngOnInit() {

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.username = this.tokenStorage.getUsername();
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  onSubmit() {

    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);

    this.authService.login(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['/']);
      },
      error => {
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      },
    );
  }

}
