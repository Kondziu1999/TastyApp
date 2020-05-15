import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-reset-password-form',
  templateUrl: './reset-password-form.component.html',
  styles: [
  ]
})
export class ResetPasswordFormComponent implements OnInit {

  public form: FormGroup;
  constructor(private formBuilder: FormBuilder,private route: ActivatedRoute) { }
  public token: string;

  ngOnInit(): void {
    this.form=this.formBuilder.group({
      password: ['', [Validators.required]],
      confirmPassword: ['',[Validators.required]]
    });
    //retrive token from 
    this.token=this.route.snapshot.paramMap.get('token');
  }

  get f(){
    return this.form.controls;
  }

  onSubmit(){

  }

}
