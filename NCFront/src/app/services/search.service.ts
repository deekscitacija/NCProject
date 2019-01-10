import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class SearchService {

  constructor(private http:  HttpClient) { }

  public executeSearch(pageNum: number, autor: string, casopis: string, naslov: string, kljucne: string, tekst: string, naucne: string[]){

    let params: any[] = [];
    
    if(pageNum){
      params.push({"key" : "pageNum", "value" : String(pageNum), "phraseQuery" : false});
    }

    if(autor){
      params.push({"key" : "autor", "value" : autor, "phraseQuery" : false});
    }
    
    if(casopis){
      params.push({"key" : "casopis", "value" : casopis, "phraseQuery" : false});
    }
    
    if(naslov){
      params.push({"key" : "naslov", "value" : naslov, "phraseQuery" : false});
    }
    
    if(kljucne){
      params.push({"key" : "kljucne", "value" : kljucne, "phraseQuery" : false});
    }
    
    if(tekst){
      params.push({"key" : "tekst", "value" : tekst, "phraseQuery" : false});
    }
    
    return this.http.post("/app/search", {"params" : params, "naucneOblasti" : naucne});
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
