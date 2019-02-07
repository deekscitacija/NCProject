import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class PaymentService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  kupiIzdanje(izdanjeId: number){

    var params = new HttpParams();
    params = params.append('izdanjeId', String(izdanjeId));

    return this.http.get("/app/kupiIzdanje", {params : params, headers : this.tokenService.headerSetup()});
  }
  

}
