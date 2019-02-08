import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'transactionType'
})
export class TransactionTypePipe implements PipeTransform {

  transform(value: any, args?: any): any {

    if(value === 'C'){
      return "Ceka";
    }else if(value === 'U'){
      return "Uspesno";
    }else if(value === 'N'){
      return "Neuspesno";
    }else if(value === 'E'){
      return "Isteklo";
    }
    return null;
  }

}
