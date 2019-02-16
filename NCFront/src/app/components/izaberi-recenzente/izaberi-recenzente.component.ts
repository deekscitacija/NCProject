import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RadService } from '../../services/rad.service';
import { RecenzijaService } from '../../services/recenzija.service';

@Component({
  selector: 'app-izaberi-recenzente',
  templateUrl: './izaberi-recenzente.component.html',
  styleUrls: ['./izaberi-recenzente.component.css']
})
export class IzaberiRecenzenteComponent implements OnInit {

  private recenzenti: any[] = [];
  private izabraniRecenzenti: any[] = [];
  private casopisId: number;
  private revizijaId: number;
  private processId: string;
  private taskId: string;
  private samoNaucna: boolean = false;

  constructor(private radService: RadService, private router: Router, private route: ActivatedRoute, private recenzijaService: RecenzijaService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.casopisId = +queryParams.get("casopis");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.revizijaId = +queryParams.get("revizijaId");
      this.getRecenzenti();
    });

  }

  getRecenzenti = function(){
    this.recenzijaService.getRecenzentiForCasopis(this.casopisId).subscribe(
      (res: any) => {
        this.recenzenti = res;
        this.izabraniRecenzenti = [];
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  getRecenzentiNaucna = function(){
    this.recenzijaService.getRecenzentiForCasopisAndNaucna(this.casopisId, this.revizijaId).subscribe(
      (res: any) => {
        this.recenzenti = res;
        this.izabraniRecenzenti = [];
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  bindRecenzenti(recenzent: any){
    var index = this.containsElement(recenzent);
    if(index == -1){
      this.izabraniRecenzenti.push(recenzent);
    }else{
      this.izabraniRecenzenti.splice(index,1);
    }
  }

  containsElement(element:any):number{
    var index = 0;
    for(let e of this.izabraniRecenzenti){
      if(e.id === element.id){
        return index;
      }
      index++;
    }
    return -1;
  }

  potvrdi = function(){
    console.log(this.izabraniRecenzenti);

    this.recenzijaService.izaberiRecenzente(this.casopisId, this.revizijaId, this.processId, this.taskId, this.izabraniRecenzenti).subscribe(
      (res: any) => {
        this.router.navigate([""]);
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  selektujNaucnaOblast = function(){
    this.samoNaucna = !this.samoNaucna;

    if(this.samoNaucna){
      this.getRecenzentiNaucna();
    }else {
      this.getRecenzenti();
    }
  }

}
