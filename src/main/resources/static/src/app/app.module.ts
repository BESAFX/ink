import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {HttpClientModule}from '@angular/common/http';
import {JwtModule} from '@auth0/angular-jwt';
import {AuthService}from '../_services/custom/auth.service';
import { httpInterceptorProviders}from '../_services/custom/auth-interceptor.service';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

export function tokenGetter() {
  return sessionStorage.getItem('AuthToken');
}

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,

    HttpClientModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: function token() {
          return sessionStorage.getItem('AuthToken');
        },
      },
    }),

  ],
  providers: [
    httpInterceptorProviders,
    AuthService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
