import { Observable } from 'rxjs';
import { CredentailsAvailability } from './../models/credentails-availability';
import { User } from './../models/user';
import { AuthService } from './../auth.service';
import { Component, OnInit, DoCheck } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { uniqueUsernameValidator } from '../validation/register-form-validators';

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
      username: ['',[Validators.required]],
      password: ['',Validators.required],
      confirmPassword: ['',Validators.required],
      email: ['',Validators.required]
    });
    //lets assume that values are valid at the beginning
  } 


  //return controls to form fields instead of making getter for each field individually 
  get f() {return this.form.controls};
  get username() {return this.form.get('username')};
  get email() {return this.form.get('email')};

  //form is valid  only if button is available to click
  onSubmit(): void {
      this.submitted=true;
      this.loading=true;

      const user=<User>JSON.parse(this.form.value);

      this.authService.register(user);

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
