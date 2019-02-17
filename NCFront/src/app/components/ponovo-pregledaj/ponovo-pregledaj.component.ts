import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RecenzijaService } from '../../services/recenzija.service'; 
import { RadService } from '../../services/rad.service'; 
import { saveAs } from 'file-saver/dist/FileSaver';

@Component({
  selector: 'app-ponovo-pregledaj',
  templateUrl: './ponovo-pregledaj.component.html',
  styleUrls: ['./ponovo-pregledaj.component.css']
})
export class PonovoPregledajComponent implements OnInit {

  private revizijaId: number;
  private processId: string;
  private taskId: string;
  private revizijaInfo : any = {};
  private ishod: string = "";

  constructor(private router: Router, private route: ActivatedRoute, private recenzijaService: RecenzijaService, private radService: RadService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getRevizija();
    });
  }

  getRevizija(){
    console.log(this.revizijaId);
    this.radService.getRevizija(this.revizijaId).subscribe(
      (res: any) => {
        this.revizijaInfo = res;
      },
      (error: any) => {
        alert('Greska');
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
        alert('Greska!');
      }
    );
  }

  potvrdi(){
    console.log(this.ishod)
    this.recenzijaService.submitPonovnoPregledanje(this.revizijaId, this.ishod, this.taskId, this.processId).subscribe(
      (res: any) => {
        this.router.navigate([""]);
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

}
