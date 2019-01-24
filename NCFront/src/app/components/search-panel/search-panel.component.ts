import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import { Router, ActivatedRoute, Params } from '../../../../node_modules/@angular/router';
import { SearchService } from '../../services/search.service';

@Component({
  selector: 'app-search-panel',
  templateUrl: './search-panel.component.html',
  styleUrls: ['./search-panel.component.css']
})
export class SearchPanelComponent implements OnInit {

  private pageNum: number = 1;
  private isLast: boolean = false;
  private results: any[] = [];

  private openSearchBox: boolean = false;
  private mode: string = '';
  private inputParam: string = '';

  private sadrzajTekst: string;
  private autorTekst: string;
  private naslovTekst: string;
  private casopisTekst: string;
  private kljucniTekst: string;

  constructor(private router : Router, private route : ActivatedRoute, private searchService: SearchService) { }

  ngOnInit() {
    this.getQueryParams();
  }

  next = function(){
    this.pageNum++;
    this.setPath();
    this.executeSearch();
  }

  prev = function(){
    if(this.pageNum <= 1)
      return;

    this.pageNum--;
    this.setPath();
    this.executeSearch();
  }

  getQueryParams = function(){
    
    this.route.queryParamMap.subscribe((queryParams)=>{

      (queryParams.has('pageNum')) ? this.pageNum = +queryParams.get("pageNum") : this.pageNum = 1;

      (queryParams.has('textContent')) ? this.sadrzajTekst = queryParams.get("textContent") : this.sadrzajTekst = "";
  
      (queryParams.has('autor')) ? this.autorTekst = queryParams.get("autor") : this.autor = "";

      (queryParams.has('magazine')) ? this.casopisTekst = queryParams.get("magazine") : this.casopisTekst = "";

      (queryParams.has('title')) ? this.naslovTekst = queryParams.get("title") : this.naslovTekst = "";
      
      (queryParams.has('keywords')) ? this.kljucniTekst = queryParams.get("keywords") : this.kljucniTekst = "";

      this.executeSearch();
      
    });
  }

  setQueryParams: any = function(){
    var queryParams: Params = {};

    if(this.pageNum){
      queryParams['pageNum'] = this.pageNum;
    }

    if(this.sadrzajTekst){
      queryParams['textContent'] = this.sadrzajTekst;
    }

    if(this.autorTekst){
      queryParams['autor'] = this.autorTekst;
    }

    if(this.casopisTekst){
      queryParams['magazine'] = this.casopisTekst;
    }

    if(this.naslovTekst){
      queryParams['title'] = this.naslovTekst;
    }

    if(this.kljucniTekst){
      queryParams['keywords'] = this.kljucniTekst;
    }

    return queryParams;
  }

  setPath = function(){
    this.router.navigate(['naucna-centrala.com/pretraga'], {queryParams : this.setQueryParams()});
  }

  obradiTekst = function(retVal: any){

    if(retVal.mode === 'tekst'){
      this.sadrzajTekst = retVal.sadrzaj;
    }else if(retVal.mode === 'autor'){
      this.autorTekst = retVal.sadrzaj;
    }else if(retVal.mode === 'casopis'){
      this.casopisTekst = retVal.sadrzaj;
    }else if(retVal.mode === 'naslov'){
      this.naslovTekst = retVal.sadrzaj;
    }else if(retVal.mode === 'kljucni'){
      this.kljucniTekst = retVal.sadrzaj;
    }

    this.openSearchBox = false;
    this.setPath();
    this.executeSearch();
  }

  manageTekst = function(mode: string){
    if(mode === 'tekst'){
      this.inputParam = this.sadrzajTekst;
    }else if(mode === 'autor'){
      this.inputParam = this.autorTekst;
    }else if(mode === 'casopis'){
      this.inputParam = this.casopisTekst;
    }else if(mode === 'naslov'){
      this.inputParam = this.naslovTekst;
    }else if(mode === 'kljucni'){
      this.inputParam = this.kljucniTekst;
    }else{
      return;
    }

    this.mode = mode;
    this.openSearchBox = !this.openSearchBox;
  }

  executeSearch = function(){
    this.searchService.executeSearch(this.pageNum, this.autorTekst, this.casopisTekst, 
      this.naslovTekst, this.kljucniTekst, this.sadrzajTekst, null).subscribe(
        (res: any) => {
          if(res){
            this.results = res.content;
            console.log(this.results);
            (this.pageNum*3 < res.totalElements) ? this.isLast = false : this.isLast = true;
          }else{
            this.results = [];
          }
        },
        (error: any) => {
          alert("Greska!");
        }
      );
  }

}
