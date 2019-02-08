import { Pipe, PipeTransform } from '@angular/core';
import { stringify } from '@angular/core/src/util';

@Pipe({
  name: 'dateDisplay'
})
export class DateDisplayPipe implements PipeTransform {

  transform(value: any, args?: any): any {

    if(!value) 
      return "";

    let dateStr = value.substring(0, 10);
    let year = dateStr.substring(0, 4);
    let month = dateStr.substring(5, 7);
    let day = dateStr.substring(8, 10);
    return day+"."+month+"."+year;
  }

}
