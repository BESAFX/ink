import{environment}from'./../../environments/environment';
import {Team}from './../../_model/team';
import {Injectable}from '@angular/core';
import {HttpClient, HttpHeaders}from '@angular/common/http';
import {Observable}from 'rxjs';

const httpOptions = {
headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable()
export class TeamService {

  private baseUrl = environment.api + '/team';

  constructor(private http: HttpClient) {
  }

  post(team: string): Observable<Team> {
    return this.http.post<Team>(this.baseUrl, team, httpOptions);
  }

  put(team: string): Observable<Team> {
    return this.http.put<Team>(this.baseUrl, team, httpOptions);
  }

  remove(id: number): Observable<any> {
    return this.http.delete(this.baseUrl + '/' + id, httpOptions);
  }

  get(id: number): Observable<Team> {
    return this.http.get<Team>(this.baseUrl + '/' + id, httpOptions);
  }

  all(): Observable<Team[]> {
    return this.http.get<Team[]>(this.baseUrl, httpOptions);
  }
}
