import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  
  private regUser: any = {ime : "", prezime : "", grad : {naziv : "", drzava : {naziv : ""}}, tip: [{kod: ""}]};

  constructor(private router: Router, private route: ActivatedRoute, private tokenService: TokenService) { }

  ngOnInit() {

    if(!localStorage.getItem('token')){
      this.router.navigate([""]);
    }

    this.tokenService.getUserFromToken().subscribe(
      (res: any) =>{
        this.regUser = res;
      },
      (error: any) =>{
        this.router.navigate([""]);
      }
    );

  }

}
