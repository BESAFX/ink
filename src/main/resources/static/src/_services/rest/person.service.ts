import{environment}from'./../../environments/environment';
import {Injectable}from '@angular/core';
import {HttpClient, HttpHeaders}from '@angular/common/http';
import {Observable}from 'rxjs';
import {Person}from '../../_model/person';

const httpOptions = {
headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable()
export class AuthService {

  private baseUrl = environment.api + '/person';

  constructor(private http: HttpClient) {
  }

  getUser(email: string): Observable<Person> {
    return this.http.get<Person>(this.baseUrl + 'findByEmail/' + email, httpOptions);
  }

  setUserLang(email: string, lang: string): Observable<Person> {
    return this.http.get<Person>(this.baseUrl + 'setUserLang/' + email + '/' + lang, httpOptions);
  }

  setUserTheme(email: string, theme: string): Observable<Person> {
    return this.http.get<Person>(this.baseUrl + 'setUserTheme/' + email + '/' + theme, httpOptions);
  }

  getUserInfo(email: string): Observable<Person> {
    return this.http.get<Person>(this.baseUrl + 'getUserInfo/' + email, httpOptions);
  }
}
