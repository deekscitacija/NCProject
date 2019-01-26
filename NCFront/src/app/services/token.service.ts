import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class TokenService {

  constructor(private http:  HttpClient) { }

  public headerSetup(): HttpHeaders{

    let headers = new HttpHeaders().set('Content-Type', 'application/json');
    headers = headers.set('token', localStorage.getItem('token'));
    return headers;
  }

  public getUserFromToken(){
    
    return this.http.get("/app/getUserFromToken", {headers : this.headerSetup()});
  }

}
