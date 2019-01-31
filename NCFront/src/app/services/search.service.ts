import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class SearchService {

  constructor(private http:  HttpClient) { }

  public executeSearch(pageNum: number, params: any[], allFields: boolean){

    return this.http.post("/app/search", {"pageNum" : pageNum, "params" : params, "allFields" : allFields});
  }

  public moreLikeThis(id: string, pageNum: number){
   
    var params = new HttpParams();
    params = params.append('pageNum', String(pageNum));
    params = params.append('documentId', id);

    return this.http.get("/app/moreLikeThis", {params : params});
  }

  public getAllCities(){

    return this.http.get("/app/getAllCities");    
  }

  public geoSearch(idx: number){

    var params = new HttpParams();
    params = params.append('cityId', String(idx));

    return this.http.get("/app/geoSearch", {params : params});
  }

  public uploadPaper(val: any, options: any){

    return this.http.post("/app/index", val, options);
  }

  private stringifyArray = function(array : any[]){

    var retVal : string = "";

    var i : number = 0;
    for(let element of array){
        retVal += element;
        i++;
        if(i<array.length){
            retVal+=",";
        }        
    }
    return retVal;
  }

}
