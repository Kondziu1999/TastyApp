import { ApiSigninResponse } from './../models/api-signin-response';
import { Subscription, Observable } from 'rxjs';
import { AuthService } from './../auth.service';
import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [
  ]
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  authObserver: Observable<ApiSigninResponse>;

  constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authService: AuthService    
  ) { }

  ngOnInit() {
      this.form = this.formBuilder.group({
          username: ['', Validators.required],
          password: ['', Validators.required]
      });

      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  get username(){return this.form.get('username')};
  get password(){return this.form.get('password')};


  onSubmit() {
      this.submitted = true;
            
      // stop here if form is invalid
      if (this.form.invalid) {
          return;
      }
      this.loading=true;

      this.authService.login(this.username.value,this.password.value)
        .pipe(first())
        .subscribe(
          data=> {
            console.log(data);
            this.router.navigate([this.returnUrl]);
          },
          error => {console.log(error),this.loading=false});
  }
}
