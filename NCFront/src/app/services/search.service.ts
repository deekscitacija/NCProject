import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { HttpParams, HttpHeaders } from '@angular/common/http';

@Injectable()
export class SearchService {

  constructor(private http:  HttpClient) { }

  public executeSearch(pageNum: number, autor: string, casopis: string, naslov: string, kljucne: string, tekst: string, naucne: string[]){

    var params = new HttpParams();
    params = params.append('pageNum', String(pageNum));

    if(autor)
      params = params.append('autor', autor);

    if(casopis)
      params = params.append('casopis', casopis);
    
    if(naslov)
      params = params.append('naslov', naslov);

    if(kljucne)
      params = params.append('kljucne', kljucne);
    
    if(tekst)
      params = params.append('tekst', tekst);

    if(naucne && naucne.length > 0)
      params = params.append('naucne', this.stringifyArray(naucne));

    return this.http.get("/app/search", {params : params});
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
