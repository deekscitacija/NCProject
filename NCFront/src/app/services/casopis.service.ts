import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class CasopisService {

  constructor(private http:  HttpClient) { }

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

}
