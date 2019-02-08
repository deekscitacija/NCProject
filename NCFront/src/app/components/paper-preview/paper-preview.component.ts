import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { TokenService } from '../../services/token.service';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-paper-preview',
  templateUrl: './paper-preview.component.html',
  styleUrls: ['./paper-preview.component.css']
})
export class PaperPreviewComponent implements OnInit {

  @Input() paper: any;
  @Input() korisnik: any;

  constructor(private tokenService: TokenService, private router: Router, private route: ActivatedRoute, private paymentService: PaymentService) { }

  ngOnInit() {
  }

  kupi = function(){
    if(!localStorage.getItem('token')){
      alert('Prijavite se pre obavljanja kupovine.');
      return;
    }

    this.paymentService.kupiRad(this.paper.id).subscribe(
      (res: any) => {
        if(res.headers.get('Location')){
          window.location.href = res.headers.get('Location');
        }

        console.log(res)
        
        if(!res.body){
          alert('Izabrano izdanje ste vec kupili ili se ceka na obradu transakcije.')
        }
      },
      (error: any) => {
        alert('Greska prilikom slanja zahteva za placanje. Pokusajte ponovo.')
      }
    );
    
  }

}
