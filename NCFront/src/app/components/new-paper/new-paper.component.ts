import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, AbstractControl, ValidatorFn } from '@angular/forms';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { SearchService } from '../../services/search.service';
import { CasopisService } from '../../services/casopis.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-new-paper',
  templateUrl: './new-paper.component.html',
  styleUrls: ['./new-paper.component.css']
})
export class NewPaperComponent implements OnInit {

  private paperForm: any = {naslov : "", apstrakt : "", kljucne : "", koautori : "", fajlovi : null, naucneOblasti : [], recenzenti : []};
  private casopisId: number;
  private naucneOblasti: any[] = [];
  private recenzenti: any[] = [];


  constructor(private searchService: SearchService, private router: Router, private route: ActivatedRoute, 
    private tokenService: TokenService, private casopisService : CasopisService) { }

  ngOnInit() {

    if(!localStorage.getItem('token')){
      this.router.navigate(['naucna-centrala.com']);
    }

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.casopisId = +queryParams.get("casopis");
    });

    this.casopisService.getNaucneOblasti().subscribe(
      (res: any) => {
        this.naucneOblasti = res;
      },
      (error: any) => {
        alert('Greska')
      }
    );

    this.casopisService.getRecenzenti(this.casopisId).subscribe(
      (res: any) => {
        this.recenzenti = res;
      },
      (error: any) => {
        alert('Greska')
      }
    );

  }

  onFileChanged = function(event: any) {
    this.paperForm.fajlovi = event.target.files[0];
  }

  submitPaper = function(){
    const uploadData = new FormData();
    uploadData.append('naslov', this.paperForm.naslov);
    uploadData.append('apstrakt', this.paperForm.apstrakt);
    uploadData.append('kljucne', this.paperForm.kljucne);
    uploadData.append('koautori', this.paperForm.koautori);
    uploadData.append('casopisId', this.casopisId);
    this.stringifyNaucneOblasti(uploadData);
    this.stringifyRecenzenti(uploadData);
    uploadData.append('fajlovi', this.paperForm.fajlovi, this.paperForm.fajlovi.name);
    var options = { content: uploadData, headers : this.tokenService.headerSetupMultipart()};
    
    console.log(this.paperForm)

    this.searchService.uploadPaper(uploadData, options).subscribe( 
      (res: any) => {
        if(res){
          this.router.navigate([""]);
        }
      },
      (error: any) =>{
        alert('Greska!')
      }
    );
  
  }

  stringifyNaucneOblasti = function(uploadData: FormData){
    let idx = 0;
    for(let naucna of this.paperForm.naucneOblasti){
      uploadData.append("naucneOblasti["+idx+"].id", naucna.id);
      uploadData.append("naucneOblasti["+idx+"].kod", naucna.kod);
      uploadData.append("naucneOblasti["+idx+"].naziv", naucna.naziv);
      idx++;
    }
  }
  
  stringifyRecenzenti = function(uploadData: FormData){
    let idx = 0;
    for(let recenzent of this.paperForm.recenzenti){
      uploadData.append("recenzenti["+idx+"].email", recenzent.email);
      uploadData.append("recenzenti["+idx+"].ime", recenzent.ime);
      uploadData.append("recenzenti["+idx+"].prezime", recenzent.prezime);
      idx++;
    }
  }

  bindNaucnaOblast = function(val: any){
    let idx = this.containsElement('N', val);
    if(idx === -1){
      this.paperForm.naucneOblasti.push(val);
      return;
    }

    this.paperForm.naucneOblasti.splice(idx, 1);
  }

  bindRecenzent = function(val: any){
    let idx = this.containsElement('R', val);
    if(idx === -1){
      this.paperForm.recenzenti.push(val);
      return;
    }

    this.paperForm.recenzenti.splice(idx, 1);
  }

  containsElement = function(mode: string, item: any) : number{
    let theList = [];
    let idx = 0;

    if(mode === 'N'){
      theList = this.paperForm.naucneOblasti;

      for(let temp of theList){
        if(temp.id === item.id){
          return idx;
        }
        idx++;
      }

    }else if(mode === 'R'){
      theList = this.paperForm.recenzenti;

      for(let temp of theList){
        if(temp.email === item.email){
          return idx;
        }
      }
      idx++;
    }
      
    return -1;
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
