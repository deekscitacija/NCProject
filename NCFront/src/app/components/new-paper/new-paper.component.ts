import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RadService } from '../../services/rad.service';
import { CasopisService } from '../../services/casopis.service';
import { TokenService } from '../../services/token.service';
import { SocketService } from '../../services/socket.service';

@Component({
  selector: 'app-new-paper',
  templateUrl: './new-paper.component.html',
  styleUrls: ['./new-paper.component.css']
})
export class NewPaperComponent implements OnInit {

  private paperForm: any = {naslov : "", apstrakt : "", kljucne : "", koautori : [{ime: "", prezime: "", adresa: "", email: ""}], fajlovi : null, naucnaOblast : {id: -1, kod: "", naziv: ""}};
  private casopisId: number;
  private processId: string;
  private naucneOblasti: any[] = [];

  constructor(private radService: RadService, private router: Router, private route: ActivatedRoute, 
    private tokenService: TokenService, private casopisService : CasopisService, private socketService : SocketService) { }

  ngOnInit() {

    if(!localStorage.getItem('token')){
      this.router.navigate(['naucna-centrala.com']);
    }

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.casopisId = +queryParams.get("casopis");
      this.processId = queryParams.get("processId");
    });

    this.casopisService.getNaucneOblastiCasopis(this.casopisId).subscribe(
      (res: any) => {
        this.naucneOblasti = res;
      },
      (error: any) => {
        alert('Greska')
      }
    );

    //this.socketService.initSocket();

  }

  onFileChanged = function(event: any) {
    this.paperForm.fajlovi = event.target.files[0];
  }

  submitPaper = function(){
    const uploadData = new FormData();
    uploadData.append('naslov', this.paperForm.naslov);
    uploadData.append('apstrakt', this.paperForm.apstrakt);
    uploadData.append('kljucne', this.paperForm.kljucne);
    uploadData.append('casopisId', this.casopisId);
    this.stringifyKoautori(uploadData);
    this.stringifyNaucnaOblast(uploadData);
    uploadData.append('fajlovi', this.paperForm.fajlovi, this.paperForm.fajlovi.name);
    uploadData.append('procesId', this.processId);
    var options = { content: uploadData, headers : this.tokenService.headerSetupMultipart()};
    
    console.log(this.paperForm)

    this.radService.posaljiRad(uploadData, options).subscribe( 
      (res: any) => {
        console.log(res)
        this.router.navigate([""]);
      },
      (error: any) =>{
        alert('Greska!')
      }
    );
    
  
  }

  dodajKoautora = function(){
    this.paperForm.koautori.push({ime: "", prezime: "", adresa: "", email: ""});
  }

  removeKoautor = function(idx: number){
    this.paperForm.koautori.splice(idx, 1);
  }

  stringifyKoautori = function(uploadData: FormData){
    let idx = 0;
    for(let koautor of this.paperForm.koautori){
      uploadData.append("koautori["+idx+"].ime", koautor.ime);
      uploadData.append("koautori["+idx+"].prezime", koautor.prezime);
      uploadData.append("koautori["+idx+"].adresa", koautor.adresa);
      uploadData.append("koautori["+idx+"].email", koautor.email);
      idx++;
    }
  }

  stringifyNaucnaOblast = function(uploadData: FormData){
    uploadData.append("naucnaOblast.id", this.paperForm.naucnaOblast.id);
    uploadData.append("naucneOblasti.kod", this.paperForm.naucnaOblast.kod);
    uploadData.append("naucneOblasti.naziv", this.paperForm.naucnaOblast.naziv);
  }

  selektujNO = function(idx: number){
    this.paperForm.naucnaOblast = this.naucneOblasti[idx];
  }

}
