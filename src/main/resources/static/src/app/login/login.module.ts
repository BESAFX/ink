import{LoginRoutingModule}from'./login-routing.module';
import { CommonModule}from '@angular/common';
import {LoginComponent}from './login.component';
import {NgModule}from '@angular/core';
import { FormsModule}from '@angular/forms';
import {RouterModule}from '@angular/router';


@NgModule({
imports: [
LoginRoutingModule,
CommonModule,
FormsModule,
RouterModule,
],
declarations: [
LoginComponent,
],
})
export class LoginModule {

}
