import { AuthService } from '../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ResetPaswordPayload } from '../models/ResetPasswordPayload';

@Component({
  selector: 'app-reset-password-component',
  templateUrl: './reset-password-component.component.html',
  styles: [
  ]
})
export class ResetPasswordComponentComponent implements OnInit {

  form: FormGroup;
  public resetRequestSend: boolean;
  public resetRequestResponseSuccessfully: boolean;
  public resetResponseRecieved: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) { }
  
  ngOnInit(): void {
    this.resetRequestSend = false;
    this.resetRequestResponseSuccessfully = true;
    this.resetResponseRecieved = false;
    this.form=this.formBuilder.group({
      usernameOrEmail: ['', [Validators.required]]
    });
  }

  get f(){
    return this.form.controls;
  }
  get usernameOrEmail(): string{
    return this.form.get('usernameOrEmail').value;
  }

  onSubmit():void{
    this.resetRequestSend = true;
    const payload = new ResetPaswordPayload(this.usernameOrEmail,null,null,null);

    this.authService.requestForResetPassConfimationEmail(payload)
      .subscribe(
        msg => {this.resetResponseRecieved=true, this.resetRequestResponseSuccessfully = true},
        error => {this.resetResponseRecieved=true, this.resetRequestResponseSuccessfully = false}
        )
    }

}
