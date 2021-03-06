import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ProcessEngineService } from '../../../services/process-engine.service';

@Component({
  selector: 'app-task-view',
  templateUrl: './task-view.component.html',
  styleUrls: ['./task-view.component.css']
})
export class TaskViewComponent implements OnInit {

  private taskList: any[] = [];

  constructor(private processEngineService: ProcessEngineService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getTaskList();
  }

  getTaskList(){

    this.processEngineService.getTaskList().subscribe(
      (res: any) => {
        if(res){
          this.taskList = res;
          console.log(this.taskList);
        }
      },
      (error: any) => {
        alert('Greska!')
      }
    );

  }

  idiNaTask = function(taskInfo: any){
    
    var variables;
    this.processEngineService.getVariableList(taskInfo.processInstanceId).subscribe(
      (res: any) => {
        if(!res){
          return;
        }
        variables = res;
        this.redirektuj(variables, taskInfo);
      },
      (error: any) => {
        alert('Greska!')
      }
    );
    
  }

  redirektuj(variables: any, taskInfo: any){
    console.log(variables)
    console.log(taskInfo)

    let queryParams: Params = {};
    queryParams['processId'] = taskInfo.processInstanceId; 

    if(taskInfo.taskId){
      queryParams['taskId'] = taskInfo.taskId; 
    }

    if(taskInfo.name === 'Unos informacija o radu'){
      queryParams['casopis'] = variables.casopisId;
      this.router.navigate(['naucna-centrala.com/novi-rad'], {queryParams : queryParams});
    }

    if(taskInfo.name === 'Pregledanje rada'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/pregledaj-rad'], {queryParams : queryParams});
    }

    if(taskInfo.name === 'Izmena prijavljenog rada'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/upload-revizija'], {queryParams : queryParams});
    }

    if(taskInfo.name === 'Izbor recenzenata'){
      queryParams['revizijaId'] = variables.revizijaId;
      queryParams['casopis'] = variables.casopisId;
      queryParams['mode'] = "BASIC";
      this.router.navigate(['naucna-centrala.com/izaberi-recenzente'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Obavljanje recenzije'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/recenzija-panel'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Analiza recenzija'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/recenzije-analiza'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Izbor novih recenzenata'){
      queryParams['revizijaId'] = variables.revizijaId;
      queryParams['casopis'] = variables.casopisId;
      queryParams['mode'] = "EXPIRED";
      this.router.navigate(['naucna-centrala.com/izaberi-recenzente'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Oznaci recenzente za zamensku recenziju'){
      queryParams['revizijaId'] = variables.revizijaId;
      queryParams['casopis'] = variables.casopisId;
      queryParams['mode'] = "GRADES";
      this.router.navigate(['naucna-centrala.com/izaberi-recenzente'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Izmene rada nakon recenzije'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/upload-revizija'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Ponovna izmena rada'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/upload-revizija'], {queryParams : queryParams});
    }

    if(taskInfo.taskName === 'Ponovno pregledanje rada'){
      queryParams['revizijaId'] = variables.revizijaId;
      this.router.navigate(['naucna-centrala.com/pregledanje-ponovo'], {queryParams : queryParams});
    }

  }


}
