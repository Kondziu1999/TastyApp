import { UserDto } from './../models/UserDto';
import { Observable } from 'rxjs';
import { CredentailsAvailability } from './../models/credentails-availability';
import { User } from './../models/user';
import { AuthService } from '../services/auth.service';
import { Component, OnInit, DoCheck } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { uniqueUsernameValidator, passwordsMismatchValidator } from '../validation/register-form-validators';

@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styles: [
  ]
})
export class RegistrationFormComponent implements OnInit {

  form: FormGroup;
  submitted: boolean=false;
  loading: boolean=false;
  registerFailure: boolean=false;

  public credentailsAvailability: CredentailsAvailability;
  public credentailsAvailabilityObservable: Observable<CredentailsAvailability>;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService) { }
    
  ngOnInit(): void {
    this.credentailsAvailability= new CredentailsAvailability(true,true);

    this.form=this.formBuilder.group({
      firstName: ['',Validators.required],
      lastName: ['',Validators.required],
      username: ['',[Validators.required,Validators.minLength(4)]],
      password: ['',[Validators.required,Validators.minLength(6)]],
      confirmPassword: ['',Validators.required,],
      email: ['',[Validators.required,Validators.email]]
    }, { validators: passwordsMismatchValidator});
    
  } 


  //return controls to form fields instead of making getter for each field individually 
  get f() {return this.form.controls};
  get username() {return this.form.get('username')};
  get email() {return this.form.get('email')};

  //form is valid  only if button is available to click
  onSubmit(): void {
      this.submitted=true;
      this.loading=true;

      console.log(this.form.value);
      const user=<User>JSON.parse(JSON.stringify(this.form.value));

      const userDto= new UserDto(user);
      console.log(userDto);
      
      this.authService.register(userDto)
        .subscribe(response=> {
          console.log(response.status);
          this.router.navigate(['/registrationSuccess']);
        },
           error => {console.log(error); 
            this.registerFailure=true
            }
        )

  }

  checkIfUsernameAndEmailAvailable(): void{
    this.credentailsAvailabilityObservable=this.authService.getUsernameAndEmailAvailability(this.username.value, this.email.value);
    this.credentailsAvailabilityObservable
      .subscribe(
        msg => {this.credentailsAvailability=msg;
        console.log(this.credentailsAvailability)
        },
        error => console.log(error)
      );
  }

  

}
