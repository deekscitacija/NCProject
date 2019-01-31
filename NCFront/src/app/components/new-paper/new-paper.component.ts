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

  private paperForm: any;
  private casopisId: number;
  private file: File = null;
  private naucneOblasti: any[] = [];

  constructor(private searchService: SearchService, private router: Router, private route: ActivatedRoute, 
    private tokenService: TokenService, private casopisService : CasopisService) { }

  ngOnInit() {

    this.paperForm = new FormGroup({
      naslov : new FormControl("",Validators.compose([
        Validators.required,
        Validators.maxLength(120)
      ])),
      apstrakt : new FormControl("",Validators.compose([
        Validators.required,
        Validators.maxLength(1000)
      ])),
      kljucne : new FormControl("",Validators.compose([
        Validators.required,
        Validators.maxLength(1000)
      ])),
      koautori : new FormControl("")
    });

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

  }

  onFileChanged = function(event: any) {
    this.file = event.target.files[0];
  }

  submitPaper = function(val: any){
    const uploadData = new FormData();
    uploadData.append('naslov', val.naslov);
    uploadData.append('apstrakt', val.apstrakt);
    uploadData.append('kljucne', val.kljucne);
    uploadData.append('koautori', val.koautori);
    uploadData.append('fajlovi', this.file, this.file.name);
    var options = { content: uploadData, headers : this.tokenService.headerSetupMultipart()};
    
    this.searchService.uploadPaper(uploadData, options).subscribe( 
      (res: any) => {
        console.log(res)
      },
      (error: any) =>{
        alert('Greska!')
      }
    );
  }

}
