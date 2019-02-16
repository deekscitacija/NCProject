import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RadService } from '../../services/rad.service';
import { TokenService } from '../../services/token.service';
import { saveAs } from 'file-saver/dist/FileSaver';

@Component({
  selector: 'app-paper-initial-approval',
  templateUrl: './paper-initial-approval.component.html',
  styleUrls: ['./paper-initial-approval.component.css']
})
export class PaperInitialApprovalComponent implements OnInit {

  private revizijaInfo: any = {naslov: "", koAutori: [], apstrakt: "", kljucneReci: "", temaOk: false, formatOk: false, prihvacen: false, 
                                autor: "", casopis: "", casopisISSN: "", naucnaOblast: "", naucnaOblastKod: "", tekstKomentara: "" }

  private revizijaId: number;  
  private processId: string;
  private taskId: string;

  constructor(private radService : RadService, private router: Router, private route: ActivatedRoute, private tokenService: TokenService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getRevizija();
    });

  }

  getRevizija(){
    this.radService.getRevizija(this.revizijaId).subscribe(
      (res: any) => {
        this.revizijaInfo = res;
        console.log(this.revizijaInfo);
      },
      (error: any) => {
        alert('Greska');
      }
    );
  }

  download(){
    this.radService.downloadRevizija(this.revizijaId).subscribe(
      (res: any) => {
        var filename = this.revizijaInfo.naslov+'.pdf';
        saveAs(res, filename);
      },
      (error: any) => {
        alert('Greska!')
      }
    )
  }

  potvrdi(){
    console.log(this.revizijaInfo)

    this.radService.inicijalniOdgovorRevizija(this.revizijaInfo, this.processId, this.taskId).subscribe(
      (res: any) =>{
        console.log(res);
        this.router.navigate([""]);
      },
      (error: any) =>{
        alert('Greska!')
      }
    );
  
  }

}
