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
  private processId: string = "";
  private formFields: any[] = [];

  constructor(private router : Router, private route : ActivatedRoute, private processEngineService: ProcessEngineService) { }

  ngOnInit() {

    this.route.queryParamMap.subscribe((queryParams)=>{
      if(queryParams.has('processId')){
        this.processId = queryParams.get("processId");
        this.getCurrentTask();
      }
    });

  }

  getCurrentTask(){
    this.processEngineService.getCurrentTask(this.processId).subscribe(
      (res: any) => {
        this.taskId = res;
        this.getFormTask();
      },
      (error: any) => {
        alert('Greska!')
      }
    );
  }


  getFormTask(){
    
    this.processEngineService.getTaskForm(this.taskId).subscribe(
      (res: any) => {
        let items = res.formFields;
        
        for(let i = 0; i < items.length; i++){
          if(items[i].key === 'registracijaUser'){
            items.splice(i, 1);
            break;
          }
        }

        this.formFields = items;
      },
      (error: any) => {
        alert('Greska!')
      }
    );
  }

  onSubmit(value, form){
    let formData = {taskId : this.taskId, processId : this.processId};

    for(var property in value){
      formData[property] = value[property];
    }

    this.processEngineService.submitTaskForm(formData).subscribe(
      (res: any) => {
        this.router.navigate([""]);
      },
      (error: any) => {
        alert('Greska!')
      }
    );
  }


}
