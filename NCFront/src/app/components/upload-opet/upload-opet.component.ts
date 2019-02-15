import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { RadService } from '../../services/rad.service';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-upload-opet',
  templateUrl: './upload-opet.component.html',
  styleUrls: ['./upload-opet.component.css']
})
export class UploadOpetComponent implements OnInit {

  private fajlovi: any = null;
  private revizijaId: number;
  private taskId: string;
  private processId: string;
  private revizijaInfo: any = {komentar: ""}

  constructor(private radService: RadService, private router: Router, private route: ActivatedRoute, 
    private tokenService: TokenService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      this.revizijaId = +queryParams.get("revizijaId");
      this.processId = queryParams.get("processId");
      this.taskId = queryParams.get("taskId");
      this.getRevizija();
    });

  }

  onFileChanged = function(event: any) {
    this.fajlovi = event.target.files[0];
  }

  getRevizija(){
    this.radService.getRevizija(this.revizijaId).subscribe(
      (res: any) => {
        this.revizijaInfo = res;
      },
      (error: any) => {
        alert('Greska!');
      }
    );
  }

  potvrdi(){

    const uploadData = new FormData();
    uploadData.append('revizijaId', String(this.revizijaId));
    uploadData.append('taskId', this.taskId);
    uploadData.append('processId', this.processId);
    uploadData.append('fajlovi', this.fajlovi, this.fajlovi.name);
    var options = { content: uploadData, headers : this.tokenService.headerSetupMultipart()};

    this.radService.uploadPonovo(uploadData, options).subscribe(
      (res: any) => {
        this.router.navigate([""]);
      },
      (error: any) => {
        alert('Vreme za formatiranje vaseg rada je isteklo.');
        this.router.navigate([""]);
      }
    );

  }

}
