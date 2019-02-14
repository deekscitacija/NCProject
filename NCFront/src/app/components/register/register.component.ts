import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '../../../../node_modules/@angular/router';
import { ProcessEngineService } from '../../services/process-engine.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private taskId: string = "";

  constructor(private router : Router, private route : ActivatedRoute, private processEngineService: ProcessEngineService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      if(queryParams.has('taskId')){
        this.taskId = queryParams.get("taskId");
        this.getFormTask();
      }
    });

  }

  getFormTask(){
    console.log(this.taskId)
    this.processEngineService.getTaskForm(this.taskId).subscribe(
      (res: any) => {
        console.log(res);
      },
      (error: any) => {
        alert('Greska!')
      }
    );

  }

}
