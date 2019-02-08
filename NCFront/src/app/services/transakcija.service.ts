import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';


@Injectable()
export class TransakcijaService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  getTransakcije(mode: string){

    var params = new HttpParams();
    params = params.append('mode', mode);

    return this.http.get("/app/getTransakcije", {params : params, headers : this.tokenService.headerSetup()});
  }

}
