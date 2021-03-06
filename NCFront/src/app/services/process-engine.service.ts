import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { TokenService } from './token.service';

@Injectable()
export class ProcessEngineService {

  constructor(private http:  HttpClient, private tokenService: TokenService) { }

  getTaskForm = function(taskId: string){

    var params = new HttpParams();
    params = params.append('taskId', taskId);
    
    return this.http.get("/app/dobaviFormuTask", {params : params});
  }

  submitTaskForm = function(val: any){
    
    return this.http.post("/app/registrujTask", val);
  }

  pokreniObjavu(magazineId: number){

    var params = new HttpParams();
    params = params.append('magazineId', String(magazineId));
    
    return this.http.get("/app/pokreniObjavu", {params : params, headers : this.tokenService.headerSetup()});
  }

  getTaskList(){

    return this.http.get("/app/getTaskForAssignee", {headers : this.tokenService.headerSetup()});
  }

  getVariableList(processInstanceId: string){

    var params = new HttpParams();
    params = params.append('processInstanceId', processInstanceId);
    
    return this.http.get("/app/getProcessVariables", {params : params});
  }

  getCurrentTask(processId: string){

    var params = new HttpParams();
    params = params.append('processId', processId);
    
    return this.http.get("/app/dobaviTrenutniTask", {params : params, responseType: 'text'})
  }

}
