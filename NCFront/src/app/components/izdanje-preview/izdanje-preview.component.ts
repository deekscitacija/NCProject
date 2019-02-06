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

  private izdanje: any = {};

  constructor(private casopisService: CasopisService, private radService: RadService, private tokenService: TokenService, 
    private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {

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

}
