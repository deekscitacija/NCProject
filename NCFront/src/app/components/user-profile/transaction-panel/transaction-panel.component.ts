import { Component, OnInit, Input } from '@angular/core';
import { TransakcijaService } from '../../../services/transakcija.service';

@Component({
  selector: 'app-transaction-panel',
  templateUrl: './transaction-panel.component.html',
  styleUrls: ['./transaction-panel.component.css']
})
export class TransactionPanelComponent implements OnInit {

  @Input() mode : string;
  private transakcije: any[] = [];

  constructor(private transakcijaService: TransakcijaService) { }

  ngOnInit() {
    
    this.transakcijaService.getTransakcije(this.mode).subscribe(
      (res: any) => {
        this.transakcije = res;
        console.log(this.transakcije)
      },
      (error: any) => {
        alert('Greska!');
      }
    );

  }


}
