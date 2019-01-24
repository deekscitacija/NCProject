import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class LoginService {

  constructor(private http:  HttpClient) { }

  login(loginInfo: any){

    return this.http.post("/app/login", loginInfo, {responseType: 'text'});
  }

}
