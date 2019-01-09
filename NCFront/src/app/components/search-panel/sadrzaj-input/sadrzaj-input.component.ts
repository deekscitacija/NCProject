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
  private placeholderText = "";

  constructor() { }

  ngOnInit() {
    this.sadrzaj = this.inputParam;

    if(this.mode === 'tekst'){
      this.placeholderText = "Unesite deo sadrzaja...";
    }else if(this.mode === 'autor'){
      this.placeholderText = "Unesite autora...";
    }else if(this.mode === 'naslov'){
      this.placeholderText = "Unesite naslov rada...";
    }else if(this.mode === 'casopis'){
      this.placeholderText = "Unesite naziv casopisa...";
    }else if(this.mode === 'kljucni'){
      this.placeholderText = "Unesite kljucne reci...";
    }

  }

  potvrdi = function(){
    this.commitSearch.emit({"sadrzaj" : this.sadrzaj, "mode" : this.mode });
  }

  odbij = function(){
    this.commitSearch.emit({"sadrzaj" : null, "mode" : this.mode });
  }

  ocisti = function(){
    this.sadrzaj = "";
  }

}
