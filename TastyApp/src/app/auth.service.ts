import { Observable } from 'rxjs';
import { ApiSigninResponse } from './models/api-signin-response';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './models/user';
import * as moment from "moment";
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginObservable: Observable<ApiSigninResponse>;
  constructor(private http:HttpClient) { }

  authServerUrlPrefix: string="http://localhost:8080";
  login(usernameOrEmail:string, password:string ) {
    console.log(usernameOrEmail+ " "+ password);
    return this.http.post<ApiSigninResponse>(this.authServerUrlPrefix+'/api/auth/signin', {usernameOrEmail, password})
      .pipe(map(response => {

        this.setSession(response);
        return response;
      }))
     

     
}
      
private setSession(authResult: ApiSigninResponse) {
    //const expiresAt = moment().add(authResult.expiresIn,'second');
    const expiresAt=authResult.expiration;

    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt) );
}          

logout() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("expires_at");
}

public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
}

isLoggedOut() {
    return !this.isLoggedIn();
}

getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
}    


}
