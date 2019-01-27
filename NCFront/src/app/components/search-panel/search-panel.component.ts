import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '../../../../node_modules/@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog } from '@angular/material';
import { SearchService } from '../../services/search.service';
import { SearchDialogComponent } from '../dialogs/search-dialog/search-dialog.component';

@Component({
  selector: 'app-search-panel',
  templateUrl: './search-panel.component.html',
  styleUrls: ['./search-panel.component.css']
})
export class SearchPanelComponent implements OnInit {

  private pageNum: number = 1;
  private isLast: boolean = false;
  private results: any[] = [];
  private advancedSearchParams: any[] = [];
  private searchInput: string = "";

  private isTextSearch: boolean = false;
  private isAllFields: boolean = true;


  constructor(private router : Router, private route : ActivatedRoute, private searchService: SearchService,
    private advancedSearchDialog: MatDialog) { }

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

  executeSearch = function(){

    let searchParams:any[] = [];
    (this.searchInput) ? searchParams.push({"optional" : false, "value" : this.searchInput, "key" : "sve", "phraseQuery" : false}) : searchParams = this.advancedSearchParams;

    console.log(this.isAllFields)

    this.searchService.executeSearch(this.pageNum, searchParams, this.isAllFields).subscribe(
        (res: any) => {
          if(res){
            this.results = res.content;
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

  executeAdvancedSearch = function(){
    let dialogRefSearch = this.advancedSearchDialog.open(SearchDialogComponent, {data: ""})

    dialogRefSearch.afterClosed().subscribe((result:any) => {
      if(result){
        this.isTextSearch = result.isTextSearch;
        this.advancedSearchParams = result.params;
        this.isAllFields = false;
        this.searchInput = "";
        this.executeSearch();
      }
    })
  }

}
