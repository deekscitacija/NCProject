import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CasopisService } from '../../services/casopis.service';
import { RadService } from '../../services/rad.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-izdanje-preview',
  templateUrl: './izdanje-preview.component.html',
  styleUrls: ['./izdanje-preview.component.css']
})
export class IzdanjePreviewComponent implements OnInit {

  private izdanje: any = {naslov : "", casopis: "", naucneOblasti : [], datumObjave : ""};
  private korisnik: any = {tip : [{kod: ""}]};

  constructor(private casopisService: CasopisService, private radService: RadService, private tokenService: TokenService, 
    private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {

    if(localStorage.getItem('token')){
      this.tokenService.getUserFromToken().subscribe(
        (res: any) => {
          this.korisnik = res;
        },
        (error: any) => {
          alert('Neophodno ponovo izvrsiti prijavu.')
        }
      )
    }

    let izdanjeId: number;
    this.route.params.subscribe(params => {
      izdanjeId = +params['id'];
    });

    this.casopisService.getIzdanje(izdanjeId).subscribe(
      (res: any) => {
        this.izdanje = res;
        console.log(this.izdanje)
      },
      (error: any) => {
        alert('Greska!')
      }
    );
  }

  kupi = function(){
    if(!localStorage.getItem('token')){
      alert('Prijavite se pre obavljanja kupovine.');
      return;
    }

    
  }

}
