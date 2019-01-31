import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CasopisService } from '../../services/casopis.service';
import { RadService } from '../../services/rad.service';

@Component({
  selector: 'app-magazine-preview',
  templateUrl: './magazine-preview.component.html',
  styleUrls: ['./magazine-preview.component.css']
})
export class MagazinePreviewComponent implements OnInit {

  private casopis: any = {};

  constructor(private casopisService: CasopisService, private radService: RadService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {

    let magazineId: number;

    this.route.params.subscribe(params => {
      magazineId = +params['id'];
    });

    this.casopisService.getMagazine(magazineId).subscribe(
      (res: any) => {
        this.casopis = res;
      },
      (error) => {
        alert('Greska');
      }
    )
  }

  objaviRad = function(){
    var queryParams: Params = {};
  
    queryParams['casopis'] = this.casopis.id;
      
    this.router.navigate(['naucna-centrala.com/novi-rad'], {queryParams : queryParams});
  }

}
