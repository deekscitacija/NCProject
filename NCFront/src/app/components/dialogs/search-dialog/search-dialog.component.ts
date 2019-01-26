import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material';
import { MAT_DIALOG_DATA } from '@angular/material';
import { P } from '@angular/core/src/render3';

@Component({
  selector: 'app-search-dialog',
  templateUrl: './search-dialog.component.html',
  styleUrls: ['./search-dialog.component.css']
})
export class SearchDialogComponent implements OnInit {

  private queryParams: any[] = [{"optional" : false, "value" : "", "key" : "", "phraseQuery" : false}];

  constructor(public dialogRef: MatDialogRef<SearchDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }

  zatvori = function(){
    this.dialogRef.close(null);
  }

  potvrdi = function(){
    let isTextSearch = false;
    if(this.queryParams.length > 0){
      isTextSearch = this.prepareParams();
    }

    this.dialogRef.close({"isTextSearch" : isTextSearch, "params" : this.queryParams });
  }

  addParam = function(){
    if(this.queryParams.length < 15){
      let newParam = {"optional" : false, "value" : "", "key" : "", "phraseQuery" : false};
      this.queryParams.push(newParam);
    }
  }

  removeLastParam = function(){
    this.queryParams.splice(-1, 1);
  }

  removeParam = function(idx: number){
    this.queryParams.splice(idx, 1);
  }

  prepareParams = function(){
    let isTextSearch = false;
    for(let i  = this.queryParams.length-1; i >= 0; i--){
      let param = this.queryParams[i];
      if(!(param.key && param.value)){
        this.queryParams.splice(i, 1);
      }else{
        if(param.key === 'tekst'){
          isTextSearch = true;
        }
      }
    }

    return isTextSearch;
  }

}
