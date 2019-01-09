import { Component, OnInit } from '@angular/core';
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
  private isSadrzajTekst: boolean = false;

  private autorTekst: string;
  private isAutorTekst: boolean = false;

  private naslovTekst: string;
  private isNaslovTekst: boolean = false;

  private casopisTekst: string;
  private isCasopisTekst: boolean = false;

  private kljucniTekst: string;
  private isKljucniTekst: boolean = false;


  constructor(private router : Router, private route : ActivatedRoute, private searchService: SearchService) { }

  ngOnInit() {

    let queryParams = this.getQueryParams();
    
    if(queryParams.has('pageNum')){
      this.pageNum = +queryParams.get("pageNum");
    }

    if(queryParams.has('textContent')){
      this.sadrzajTekst = queryParams.get("textContent");
      this.isSadrzajTekst = true;
    }

    if(queryParams.has('autor')){
      this.autorTekst = queryParams.get("autor");
      this.isAutorTekst = true;
    }

    if(queryParams.has('magazine')){
      this.casopisTekst = queryParams.get("magazine");
      this.isCasopisTekst = true;
    }

    if(queryParams.has('title')){
      this.naslovTekst = queryParams.get("title");
      this.isNaslovTekst = true;
    }

    if(queryParams.has('keywords')){
      this.kljucniTekst = queryParams.get("keywords");
      this.isKljucniTekst = true;
    }
    
  }

  next = function(){
    this.pageNum++;
    this.setPath();
  }

  prev = function(){
    if(this.pageNum <= 1)
      return;

    this.pageNum--;
    this.setPath();
  }

  getQueryParams: any = function(){
    let retVal: Params = null;
    this.route.queryParamMap.subscribe((queryParams)=>{
      retVal = queryParams;
    });
    return retVal;
  }

  setQueryParams: any = function(){
    var queryParams: Params = {};

    if(this.pageNum){
      queryParams['pageNum'] = this.pageNum;
    }

    if(this.sadrzajTekst){
      queryParams['textContent'] = this.sadrzajTekst;
      this.isSadrzajTekst = true;
    }else{
      this.isSadrzajTekst = false;
    }

    if(this.autorTekst){
      queryParams['autor'] = this.autorTekst;
      this.isAutorTekst = true;
    }else{
      this.isAutorTekst = false;
    }

    if(this.casopisTekst){
      queryParams['magazine'] = this.casopisTekst;
      this.isCasopisTekst = true;
    }else{
      this.isCasopisTekst = false;
    }

    if(this.naslovTekst){
      queryParams['title'] = this.naslovTekst;
      this.isNaslovTekst = true;
    }else{
      this.isNaslovTekst = false;
    }

    if(this.kljucniTekst){
      queryParams['keywords'] = this.kljucniTekst;
      this.isKljucniTekst = true;
    }else{
      this.isKljucniTekst = false;
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
            console.log(this.results)
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
