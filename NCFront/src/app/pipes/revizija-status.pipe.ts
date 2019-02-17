import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'revizijaStatus'
})
export class RevizijaStatusPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if(value === "M")
      return "Prihvatiti uz manje ispravke";
    if(value === "P")
      return "Prihvatiti";
    if(value === "U")
      return "Uslovno prihvatiti";
    if(value === "O")
      return "Odbiti";
    return "";
  }

}
