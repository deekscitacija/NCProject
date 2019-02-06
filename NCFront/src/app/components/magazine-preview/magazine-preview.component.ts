import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CasopisService } from '../../services/casopis.service';
import { RadService } from '../../services/rad.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-magazine-preview',
  templateUrl: './magazine-preview.component.html',
  styleUrls: ['./magazine-preview.component.css']
})
export class MagazinePreviewComponent implements OnInit {

  private casopis: any = {id: "", issn : "", urednik : "", naucneOblasti : []};
  private izdanja: any[] = [];
  private korisnik: any = {};
  private pageNum: number = 1;
  private isLast: boolean = false;

  constructor(private casopisService: CasopisService, private radService: RadService, private tokenService: TokenService, 
    private router: Router, private route: ActivatedRoute) { }

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
      
    this.router.navigate(['naucna-centrala.com/novi-rad'], {queryParams : queryParams});
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
