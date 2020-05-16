import { ResetPaswordPayload } from './../models/ResetPasswordPayload';
import { AuthService } from '../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { passwordsMismatchValidator } from '../validation/register-form-validators';

@Component({
  selector: 'app-reset-password-form',
  templateUrl: './reset-password-form.component.html',
  styles: [
  ]
})
export class ResetPasswordFormComponent implements OnInit {

  public form: FormGroup;
  constructor(private formBuilder: FormBuilder,private route: ActivatedRoute,private authService:AuthService) { }
  public token: string;
  waitingForApiResponse: boolean;
  apiError: boolean;

  ngOnInit(): void {
    this.waitingForApiResponse=false;
    this.apiError=false;

    this.form=this.formBuilder.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['',[Validators.required]]
    },{ validators: passwordsMismatchValidator});
    //retrive token from 
    this.token=this.route.snapshot.paramMap.get('token');
  }

  get f(){
    return this.form.controls;
  }
  get password() : string{
    return this.form.get('password').value;
  }

  onSubmit(){
    this.waitingForApiResponse=true;
    const payload = new ResetPaswordPayload(null, this.password,this.token,null);
    this.authService.resetPassword(payload)
      .subscribe(
        message => {this.waitingForApiResponse=true},
        error => {console.log(error); this.apiError=true}
      )
  }

}
