import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-reset-password-component',
  templateUrl: './reset-password-component.component.html',
  styles: [
  ]
})
export class ResetPasswordComponentComponent implements OnInit {

  form: FormGroup;
  public resetRequestSend: boolean;
  constructor(private formBuilder: FormBuilder) { }
  
  ngOnInit(): void {
    this.resetRequestSend=false;

    this.form=this.formBuilder.group({
      email: ['', [Validators.required,Validators.email]]
    });
  }

  get f(){
    return this.form.controls;
  }

  onSubmit():void{
    this.resetRequestSend=true;
    }

}
