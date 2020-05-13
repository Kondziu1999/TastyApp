import { Observable } from 'rxjs';
import { ValidatorFn, AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';
import { CredentailsAvailability } from '../models/credentails-availability';


// export RegisterFormValidators implements Validator {

// }
export function uniqueUsernameValidator(credentailsAvailability: CredentailsAvailability): ValidatorFn {
    
    
    return (control: AbstractControl): {[key: string]: any} | null => {
        //if not available return forbiddenName
        console.log("ja jebie czemu to nie działa: "+credentailsAvailability.usernameAvailable);
        
        return !credentailsAvailability.usernameAvailable? {'notUniqueUsername': true} : null;
      };
} 


export const passwordsMismatchValidator: ValidatorFn=(control: FormGroup): ValidationErrors| null =>{
  const password=control.get('password').value;
  const confirmPassword=control.get('confirmPassword').value;
  const valid=password && confirmPassword && password===confirmPassword;

  return valid ? null: {'passwordMismatch' : true};
};


