import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class RecenzijaService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  getRecenzentiForCasopis(casopisId: number){

    let params = new HttpParams();
    params = params.append("casopisId", String(casopisId));

    return this.http.get("/app/getRecenzentiCasopis", {params : params});
  }

  getRecenzentiForCasopisAndNaucna(casopisId: number, revizijaId: number){

    let params = new HttpParams();
    params = params.append("casopisId", String(casopisId));
    params = params.append("revizijaId", String(revizijaId));

    return this.http.get("/app/getRecenzentiCasopisAndNaucna", {params : params});
  }

  izaberiRecenzente(casopisId: number, revizijaId: number, processId: string, taskId: string, val: any[]){

    let params = new HttpParams();
    params = params.append("casopisId", String(casopisId));
    params = params.append("revizijaId", String(revizijaId));
    params = params.append("processId", processId);
    params = params.append("taskId", taskId);

    return this.http.post("/app/izaberiRecenzente", val, {params : params});
  }

}
