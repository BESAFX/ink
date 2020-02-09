import{environment}from'./../../environments/environment';
import {Injectable}from '@angular/core';
import {HttpClient, HttpHeaders}from '@angular/common/http';
import {Observable}from 'rxjs';

import {JwtResponse}from '../../_dto/jwt-response';
import {AuthLoginInfo}from '../../_dto/login-info';

const httpOptions = {
headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(environment.api + '/login', credentials, httpOptions);
  }

}
