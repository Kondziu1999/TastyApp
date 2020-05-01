import { ValidatorFn, AbstractControl } from '@angular/forms';

export function emptyIngredientValidator(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} | null => {

      const isEmpty= (<string>(control.value)).length==0;
      return isEmpty ? {'isEmpty': {value: control.value}} : null;
    };
  }

export function notRequiredLengthOfStepValidator(): ValidatorFn {
return (control: AbstractControl): {[key: string]: any} | null => {

    const isEnoughLong= (<string>(control.value)).length<10;
    return isEnoughLong ? {'isEnoughLong': {value: control.value}} : null;
};
}

