import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class RadService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  pokreniObjavu(magazineId: number){

    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));

    return this.http.get("/app/pokreniObjavu", {params : params, headers : this.tokenService.headerSetup()});
  }

  download(paperId: number){

    var params = new HttpParams();
    params = params.append('paperId', String(paperId));

    return this.http.get("/app/download", {params : params, responseType: 'blob'});
  }

  public posaljiRad(val: any, options: any){

    return this.http.post("/app/posaljiRad", val, options);
  }
  

}
