import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-magazine-list',
  templateUrl: './magazine-list.component.html',
  styleUrls: ['./magazine-list.component.css']
})
export class MagazineListComponent implements OnInit {

  @Input() magazine: any;

  constructor() { }

  ngOnInit() {
  }

}
