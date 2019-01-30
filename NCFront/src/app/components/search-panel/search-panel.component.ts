import { Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
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
  private isMoreLikeThis: boolean = false;


  constructor(private router : Router, private route : ActivatedRoute, private searchService: SearchService,
    private advancedSearchDialog: MatDialog) { }

  ngOnInit() {
    
  }

  next = function(){
    this.pageNum++;
    (this.isMoreLikeThis) ? this.moreLikeThis : this.executeSearch();
  }

  prev = function(){
    if(this.pageNum <= 1)
      return;

    this.pageNum--;
    (this.isMoreLikeThis) ? this.moreLikeThis : this.executeSearch();
  }

  executeSearch = function(){

    let searchParams:any[] = [];
    if(this.searchInput) {
      searchParams.push({"optional" : false, "value" : this.searchInput, "key" : "sve", "phraseQuery" : false}) 
      this.advancedSearchParams = [];
      this.isAllFields = true;
    }else {
      searchParams = this.advancedSearchParams;
      this.isAllFields = false;
    }
    this.isMoreLikeThis = false;

    this.searchService.executeSearch(this.pageNum, searchParams, this.isAllFields).subscribe(
        (res: any) => {
          if(res){
            this.results = res.content;
            if(this.results){
              (this.results.length < 3) ? this.isLast = true : this.isLast = false;
            }
          }else{
            this.results = [];
            if(this.pageNum > 1){
              this.pageNum = this.pageNum - 1;
              this.executeSearch();
            }
          }
        },
        (error: any) => {
          alert("Greska!");
        }
      );
  }

  executeAdvancedSearch = function(){
    this.isMoreLikeThis = false;
    let dialogRefSearch = this.advancedSearchDialog.open(SearchDialogComponent, {data: this.advancedSearchParams})

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

  executeSearchMethod = function(){
    this.pageNum = 1;
    this.executeSearch();
  }

  clearSearch = function(){
    this.advancedSearchParams = [];
    this.searchInput = "";
  }

  moreLikeThis = function(id: string){
    console.log(id);
    this.isMoreLikeThis = true;
    this.searchService.moreLikeThis(id, this.pageNum).subscribe(
      (res: any) => {
        if(res){
          this.results = res.content;
          if(this.results){
            (this.results.length < 3) ? this.isLast = true : this.isLast = false;
          }
        }else{
          this.results = [];
          if(this.pageNum > 1){
            this.pageNum = this.pageNum - 1;
            this.moreLikeThis();
          }
        }
        console.log(this.results)
      },
      (error: any) =>{
        alert('Greska!');
      }
    );
  }

}
