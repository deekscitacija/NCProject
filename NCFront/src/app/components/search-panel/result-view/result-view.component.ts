import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { SearchService } from '../../../services/search.service';
import { RadService } from '../../../services/rad.service';
import { saveAs } from 'file-saver/dist/FileSaver';

@Component({
  selector: 'app-result-view',
  templateUrl: './result-view.component.html',
  styleUrls: ['./result-view.component.css']
})
export class ResultViewComponent implements OnInit {

  @Input() theResult: any;
  @Output() docId: EventEmitter<any> = new EventEmitter<any>();

  constructor(private searchService: SearchService, private radService: RadService) { }

  ngOnInit() {
    
  }

  moreLikeThis = function(id: string){
    this.docId.emit(id);
  }

  download = function(paperId: number, naslov: string){
    
    this.radService.download(paperId).subscribe(
      (res: any) => {
        const filename = naslov+'.pdf';
        saveAs(res, filename);
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

}
