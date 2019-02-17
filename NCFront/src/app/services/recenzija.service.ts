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

  getKomentari(revizijaId: number){

    let params = new HttpParams();
    params = params.append("revizijaId", String(revizijaId));

    return this.http.get("/app/getKomentari", {params : params});
  }

  komentarisi(revizijaId: number, val: any){

    let params = new HttpParams();
    params = params.append("revizijaId", String(revizijaId));

    return this.http.post("/app/komentarisi", val, {params : params, headers : this.tokenService.headerSetup()});
  }

  submitRecenzija(revizijaId: number, processId: string, taskId: string, revizijaStatus: string){

    let params = new HttpParams();
    params = params.append("revizijaId", String(revizijaId));
    params = params.append("processId", processId);
    params = params.append("taskId", taskId);
    params = params.append("revizijaStatus", revizijaStatus);

    console.log(revizijaId);
    console.log(processId);
    console.log(taskId);
    console.log(revizijaStatus);
    console.log(localStorage.getItem('token'));

    return this.http.get("/app/submitRecenzija", {params : params, headers : this.tokenService.headerSetup()});

  }

}
