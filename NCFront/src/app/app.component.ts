import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '../../node_modules/@angular/router';
import { LoginService } from './services/login.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  private korisnik : any;
  private emailPass : any = {"email" : "", "password" : ""};
  private isPrijava = false;

  constructor(private router : Router, private route : ActivatedRoute, private loginService: LoginService) { }

  ngOnInit() {

    if(localStorage.getItem('userToken')){
      console.log(localStorage.getItem('userToken'))
    }

  }

  pretrazi = function(){
    this.router.navigate(['/naucna-centrala.com/pretraga']);
  }

  login = function(){
    this.isPrijava = true;
    this.emailPass = {"email" : "", "password" : ""};
  }

  potvrdi = function(){
    console.log(this.emailPass)
    this.loginService.login(this.emailPass).subscribe(
      (res: any)=>{
        localStorage.setItem('token', res);
        this.isPrijava = false;
      },
      (error: any)=>{
        alert('Greska!');
      }
    );
  }

  odbij = function(){
    this.isPrijava = false;
    this.emailPass = {"email" : "", "password" : ""};
  }

}
