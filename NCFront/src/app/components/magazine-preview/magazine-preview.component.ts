import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
    
  }

}
