import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SearchService } from '../../../services/search.service';
import { appInitializerFactory } from '@angular/platform-browser/src/browser/server-transition';

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() theResult: any;
  @Output() docId: EventEmitter<any> = new EventEmitter<any>();

  constructor(private searchService: SearchService) { }

  ngOnInit() {
    
  }

  moreLikeThis = function(id: string){
    this.docId.emit(id);
  }

}
