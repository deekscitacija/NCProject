import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CasopisService } from '../../services/casopis.service';
import { RadService } from '../../services/rad.service';
import { TokenService } from '../../services/token.service';
import { PaymentService } from '../../services/payment.service';
import { ProcessEngineService } from '../../services/process-engine.service';

@Component({
  selector: 'app-magazine-preview',
  templateUrl: './magazine-preview.component.html',
  styleUrls: ['./magazine-preview.component.css']
})
export class MagazinePreviewComponent implements OnInit {

  private casopis: any = {id: "", issn : "", urednik : "", naucneOblasti : []};
  private izdanja: any[] = [];
  private korisnik: any = {tip: [{kod: ""}] };
  private pageNum: number = 1;
  private isLast: boolean = false;

  constructor(private casopisService: CasopisService, private radService: RadService, private tokenService: TokenService, 
    private router: Router, private route: ActivatedRoute, private paymentService: PaymentService, private processEngineService: ProcessEngineService) { }

  ngOnInit() {

    let magazineId: number;

    this.route.params.subscribe(params => {
      magazineId = +params['id'];
    });

    this.casopisService.getMagazine(magazineId).subscribe(
      (res: any) => {
        this.casopis = res;
        this.getIzdanja();
      },
      (error) => {
        alert('Greska');
      }
    );

    if(localStorage.getItem('token')){
      
      this.tokenService.getUserFromToken().subscribe(
        (res: any) => {
          this.korisnik = res;
          console.log(this.korisnik);
        },
        (error: any) => {
          alert('Greska!')
        }
      );
    }

  }

  objaviRad = function(){
    var queryParams: Params = {};
    queryParams['casopis'] = this.casopis.id;
    
    this.processEngineService.pokreniObjavu(this.casopis.id).subscribe(
      (res: any) => {
        queryParams['processId'] = res.id;
        if(localStorage.getItem('token')){
          this.router.navigate(['naucna-centrala.com/novi-rad'], {queryParams : queryParams});
        }else{
          this.router.navigate(['naucna-centrala.com/registracija'], {queryParams : queryParams});
        }
      },
      (error: any) => {
        alert('Greska!')
      }
    );
  }

  getIzdanja = function(){

    this.casopisService.getIzdanja(this.casopis.id, this.pageNum).subscribe(
      (res: any) => {
        this.izdanja = res.content;
        this.isLast = res.last;
      },
      (error) => {
        alert('Greska');
      }
    )

  }

  pretplatiSe(){
    if(!localStorage.getItem('token')){
      alert('Prijavite se pre obavljanja kupovine.');
      return;
    }

    this.paymentService.pretplata(this.casopis.id).subscribe(
      (res: any) => {
        if(res.headers.get('Location')){
          window.location.href = res.headers.get('Location');
        }
        
        if(!res.body){
          alert('Vec ste se pretplatili ili se ceka na obradu transakcije.')
        }
      },
      (error: any) => {
        alert('Greska prilikom slanja zahteva za placanje. Pokusajte ponovo.')
      }
    );
  }

  otvoriIzdanje = function(izdanjeId: number){
    this.router.navigate(['naucna-centrala.com/izdanje/'+izdanjeId]);
  }

  next = function(){
    this.pageNum++;
    this.getIzdanja();
  }

  prev = function(){
    if(this.pageNum <= 1)
      return;

    this.pageNum--;
    this.getIzdanja();
  }

}
