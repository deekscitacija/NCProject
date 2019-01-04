import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '../../../../node_modules/@angular/router';
import { CasopisService } from '../../services/casopis.service'

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  private magazines: any[];
  private pageNum: number = 1;
  private isLast: boolean = false;

  constructor(private casopisService: CasopisService, private router : Router, private route : ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParamMap.subscribe((queryParams)=>{
      if(queryParams.has('pageNum')){
        this.pageNum = +queryParams.get("pageNum");
      }
      this.loadMagazines();
    });
  }

  loadMagazines = function(){
    this.casopisService.getMagazinePage(this.pageNum-1).subscribe(
      (res: any) => {
        this.magazines = res.content;
        this.isLast = res.last;
      },
      (error: any) => {
        alert(error)
      }
    );
  }

  next = function(){
    this.pageNum++;
    this.setPageNum();
    this.loadMagazines();
  }

  prev = function(){
    if(this.pageNum <= 1)
      return;

    this.pageNum--;
    this.setPageNum();
    this.loadMagazines();
  }

  setPageNum = function(){
    var queryParams: Params = {'pageNum' : this.pageNum};
    this.router.navigate([''],{queryParams : queryParams});
  }

}
