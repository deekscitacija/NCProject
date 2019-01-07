import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { modelGroupProvider } from '@angular/forms/src/directives/ng_model_group';

@Component({
  selector: 'app-sadrzaj-input',
  templateUrl: './sadrzaj-input.component.html',
  styleUrls: []
})
export class SadrzajInputComponent implements OnInit {

  @Input() inputParam: any;
  @Input() mode: any;
  @Output() commitSearch = new EventEmitter<any>();
  private sadrzaj: string = "";

  constructor() { }

  ngOnInit() {
    this.sadrzaj = this.inputParam;
  }

  potvrdi = function(){
    this.commitSearch.emit({"sadrzaj" : this.sadrzaj, "mode" : this.mode });
  }

  odbij = function(){
    this.commitSearch.emit({"sadrzaj" : null, "mode" : this.mode });
  }

}
