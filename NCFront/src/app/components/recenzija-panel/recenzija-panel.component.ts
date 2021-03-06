import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RecenzijaService } from '../../services/recenzija.service'; 
import { RadService } from '../../services/rad.service'; 
import { saveAs } from 'file-saver/dist/FileSaver';

@Component({
  selector: 'app-recenzija-panel',
  templateUrl: './recenzija-panel.component.html',
  styleUrls: ['./recenzija-panel.component.css']
})
export class RecenzijaPanelComponent implements OnInit {

  private isKomentar: boolean = false;
  private komentari: any[] = [];
  private revizijaId: number;
  private processId: string;
  private taskId: string;
  private revizijaStatus: string;
  private revizijaInfo : any = {};

  constructor(private router: Router, private route: ActivatedRoute, private recenzijaService: RecenzijaService, private radService: RadService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getKomentari();
      this.getRevizija();
    });

  }

  getRevizija(){
    this.radService.getRevizija(this.revizijaId).subscribe(
      (res: any) => {
        this.revizijaInfo = res;
      },
      (error: any) => {
        alert('Greska');
      }
    );
  }

  getKomentari(){
    this.recenzijaService.getKomentari(this.revizijaId).subscribe(
      (res: any) => {
        this.komentari = res;
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  komentarisi = function(){
    this.isKomentar = !this.isKomentar;
  }

  potvrdi = function(){

    this.recenzijaService.submitRecenzija(this.revizijaId, this.processId, this.taskId, this.revizijaStatus).subscribe(
      (res: any) => {
        this.router.navigate([""]);
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  download(){
    this.radService.downloadRevizija(this.revizijaId).subscribe(
      (res: any) => {
        console.log(res)
        var filename = this.revizijaInfo.naslov+'.pdf';
        saveAs(res, filename);
      },
      (error: any) => {
        alert('Greska!')
      }
    )
  }

}
