import { Observable } from 'rxjs';
import { ValidatorFn, AbstractControl } from '@angular/forms';
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


