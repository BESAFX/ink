import{environment}from'./../../environments/environment';
import {Company}from './../../_model/company';
import {Injectable}from '@angular/core';
import {HttpClient, HttpHeaders}from '@angular/common/http';
import {Observable}from 'rxjs';

const httpOptions = {
headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable()
export class CompanyService {

  private baseUrl = environment.api + '/company';

  constructor(private http: HttpClient) {
  }

  post(company: string): Observable<Company> {
    return this.http.post<Company>(this.baseUrl, company, httpOptions);
  }

  put(company: string): Observable<Company> {
    return this.http.put<Company>(this.baseUrl, company, httpOptions);
  }

  remove(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + '/' + id, httpOptions);
  }

  get(id: number): Observable<Company> {
    return this.http.get<Company>(this.baseUrl + '/' + id, httpOptions);
  }

  all(): Observable<Company[]> {
    return this.http.get<Company[]>(this.baseUrl, httpOptions);
  }
}
