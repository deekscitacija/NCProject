import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class PaymentService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  kupiIzdanje(izdanjeId: number){

    var params = new HttpParams();
    params.append('izdanjeId', String(izdanjeId));

    return this.http.get("/app/kupiIzdanje", {params : params, headers : this.tokenService.headerSetup(), observe: 'response'});
  }

  kupiRad(radId: number){

    var params = new HttpParams();
    params = params.append('radId', String(radId));

    return this.http.get("/app/kupiRad", {params : params, headers : this.tokenService.headerSetup(), observe: 'response'});
  }

  pretplata(magazineId: number){

    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));

    return this.http.get("/app/pretplata", {params : params, headers : this.tokenService.headerSetup(), observe: 'response'});
  }
  

}
