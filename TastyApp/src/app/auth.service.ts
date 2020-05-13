import { UserDto } from './models/userDto';
import { Observable } from 'rxjs';
import { ApiSigninResponse } from './models/api-signin-response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './models/user';
import * as moment from "moment";
import { map } from 'rxjs/operators';
import { CredentailsAvailability } from './models/credentails-availability';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginObservable: Observable<ApiSigninResponse>;
  
  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
    })
  };

  private JsonHeader=new HttpHeaders({'Content-Type':  'application/json'});

  authServerUrlPrefix: string="http://localhost:8080";


  
  constructor(private http:HttpClient) { }

  
  getUsernameAndEmailAvailability(username: string, email: string){
    return this.http.post<CredentailsAvailability>(this.authServerUrlPrefix+'/api/auth/checkCredentials',{username,email});

  }

  login(usernameOrEmail:string, password:string ) {
    console.log(usernameOrEmail+ " "+ password);
    return this.http.post<ApiSigninResponse>(this.authServerUrlPrefix+'/api/auth/signin', {usernameOrEmail, password})
      .pipe(map(response => {

        this.setSession(response);
        return response;
      }))
     
  }

  register(userDto : UserDto){
    return this.http.post(this.authServerUrlPrefix+ '/api/auth/signup',JSON.stringify(userDto), {observe: 'response', headers: this.JsonHeader});
  }
  
      
private setSession(authResult: ApiSigninResponse) {
    //const expiresAt = moment().add(authResult.expiresIn,'second');
    const expiresAt=<number>authResult.expiration;
    const time=moment();
    time.add(expiresAt,'seconds');
    console.log(time.format());
    console.log(moment().isBefore(time));

    localStorage.setItem('access_token', authResult.accessToken);
    localStorage.setItem("expires_at", JSON.stringify(time) );
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
