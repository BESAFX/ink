import{environment}from'./../../environments/environment';
import {Injectable}from '@angular/core';
import {HttpClient, HttpHeaders}from '@angular/common/http';
import {Observable}from 'rxjs';
import {Contact}from '../../_model/contact';

const httpOptions = {
headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private baseUrl = environment.api + '/contact';

  constructor(private http: HttpClient) {
  }

  post(contact: string): Observable<Contact> {
    return this.http.post<Contact>(this.baseUrl, contact, httpOptions);
  }

  put(contact: string): Observable<Contact> {
    return this.http.put<Contact>(this.baseUrl, contact, httpOptions);
  }

  remove(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + '/' + id, httpOptions);
  }

  get(id: number): Observable<Contact> {
    return this.http.get<Contact>(this.baseUrl + '/' + id, httpOptions);
  }

  all(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.baseUrl, httpOptions);
  }
}
