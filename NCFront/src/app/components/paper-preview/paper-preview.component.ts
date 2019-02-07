import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-paper-preview',
  templateUrl: './paper-preview.component.html',
  styleUrls: ['./paper-preview.component.css']
})
export class PaperPreviewComponent implements OnInit {

  @Input() paper: any;
  @Input() korisnik: any;

  constructor() { }

  ngOnInit() {
  }

}
