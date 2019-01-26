import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class SearchService {

  constructor(private http:  HttpClient) { }

  public executeSearch(pageNum: number, params: any[]){

    return this.http.post("/app/search", {"pageNum" : pageNum, "params" : params});
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
