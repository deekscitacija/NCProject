import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RecenzijaService } from '../../services/recenzija.service'; 

@Component({
  selector: 'app-analiza-recenzija',
  templateUrl: './analiza-recenzija.component.html',
  styleUrls: ['./analiza-recenzija.component.css']
})
export class AnalizaRecenzijaComponent implements OnInit {

  private revizijaId: number;
  private processId: string;
  private taskId: string;
  private komentari: any[] = [];
  private ocene: any[] = [];

  constructor(private router: Router, private route: ActivatedRoute, private recenzijaService: RecenzijaService) { }

  ngOnInit() {
    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getKomentari();
      this.getOcene();
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

  getOcene(){
    this.recenzijaService.getOcene(this.revizijaId).subscribe(
      (res: any) => {
        this.ocene = res;
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

}
