import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class CasopisService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  getMagazinePage(pageNum: number){

    var params = new HttpParams();
    params = params.append('pageNum', String(pageNum));

    return this.http.get("/app/getPageMagazine", {params : params});
  }

  getMagazine(magazineId: number){

    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));

    return this.http.get("/app/getMagazine", {params : params});
  }

  getNaucneOblasti(){

    return this.http.get("/app/getNaucneOblasti");
  }

  getRecenzenti(magazineId: number){
    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));

    return this.http.get("/app/getRecenzenti", {params : params});
  }

  getIzdanja(magazineId: number, pageNum: number){
    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));
    params = params.append('pageNum', String(pageNum));

    return this.http.get("/app/getIzdanja", {params : params});
  }

  getIzdanje(izdanjeId: number){
    var params = new HttpParams();
    params = params.append('izdanjeId', String(izdanjeId));
  
    return this.http.get("/app/getIzdanje", {params : params});
  }

}
