import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RecenzijaService } from '../../services/recenzija.service'; 

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

  constructor(private router: Router, private route: ActivatedRoute, private recenzijaService: RecenzijaService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getKomentari();
    });

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

}
