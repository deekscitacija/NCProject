import { Component, OnInit, Input } from '@angular/core';
import { RecenzijaService } from '../../services/recenzija.service'; 

@Component({
  selector: 'app-komentar-component',
  templateUrl: './komentar-component.component.html',
  styleUrls: ['./komentar-component.component.css']
})
export class KomentarComponentComponent implements OnInit {

  @Input() revizijaId: number;
  private komentar = {vidljivost: "SVI", tekst: ""}

  constructor(private recenzijaService:  RecenzijaService) { }

  ngOnInit() {
  }

  potvrdi = function(){
    this.recenzijaService.komentarisi(this.revizijaId, this.komentar).subscribe(
      (res: any) => {
        window.location.reload();
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

}
