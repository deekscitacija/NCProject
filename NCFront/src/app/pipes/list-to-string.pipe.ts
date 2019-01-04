import { Pipe, PipeTransform } from '@angular/core';
import { Conditional } from '@angular/compiler';

@Pipe({
  name: 'listToString'
})
export class ListToStringPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    
    let retVal = "";

    for(let i = 0; i < value.length; i++){
      retVal = retVal+value[i].naziv;
      if(i < value.length-1){
        retVal = retVal+", ";
      }
    }

    return retVal;
  }

}
