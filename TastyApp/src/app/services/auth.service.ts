import { ResetPaswordPayload } from '../models/ResetPasswordPayload';
import { UserDto } from '../models/userDto';
import { Observable, BehaviorSubject } from 'rxjs';
import { ApiSigninResponse } from '../models/api-signin-response';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as moment from "moment";
import { map } from 'rxjs/operators';
import { CredentailsAvailability } from '../models/credentails-availability';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginObservable: Observable<ApiSigninResponse>;
  
  // private httpOptions = {
  //   headers: new HttpHeaders({
  //     'Content-Type':  'application/json',
  //   })
  // };

  private JsonHeader=new HttpHeaders({'Content-Type':  'application/json'});

  frontendUrlPrefix: string = "http://localhost:4200";
  authServerUrlPrefix: string = "http://localhost:8080";

  private isUserLoggedInSubject: BehaviorSubject<boolean>;

  
  constructor(private http:HttpClient) {
    const isUserLoggedIn=
    (moment().isBefore(this.getExpiration()) !=null)?
        moment().isBefore(this.getExpiration()) : false;


    this.isUserLoggedInSubject= new BehaviorSubject<boolean>(isUserLoggedIn);
   }

  
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
      localStorage.setItem("user_id", authResult.userId.toString());
      this.isUserLoggedInSubject.next(true);
      console.log("insisde setSession" + true); 

  }          

  logout() {
      localStorage.removeItem("access_token");
      localStorage.removeItem("expires_at");
      localStorage.removeItem("user_id");
      this.isUserLoggedInSubject.next(false);
      console.log("insisde logout " + false);

  }

  public isLoggedIn() {
      const checkIfLoggedIn = (moment().isBefore(this.getExpiration()) != null) ? moment().isBefore(this.getExpiration()) : false;

      if(checkIfLoggedIn != this.isUserLoggedInSubject.value){
        this.isUserLoggedInSubject.next(checkIfLoggedIn);
      }
      return this.isUserLoggedInSubject;
  }

  isLoggedOut() {
      return !this.isUserLoggedInSubject.value;
  } 

  getExpiration() {
      const expiration = localStorage.getItem("expires_at");
      const expiresAt = JSON.parse(expiration);
      return moment(expiresAt);
  }    

  resetPassword(payload: ResetPaswordPayload){
    //it will return same payload but without password and token
    return this.http.post<ResetPaswordPayload>(this.authServerUrlPrefix + '/api/auth/resetPassword', payload,{headers: this.JsonHeader});
  }

  //same as above but different fields in payload(usernameOrEmail and frontendUrl[added here])
  requestForResetPassConfimationEmail(payload: ResetPaswordPayload){
    const frontendResponseUrl = this.frontendUrlPrefix+ '/resetPassword';
    payload.frontendUrl=frontendResponseUrl;
    return this.http.post<ResetPaswordPayload>(this.authServerUrlPrefix + '/api/auth/resetPassword', payload,{headers: this.JsonHeader});
    
  } 


  getIdOfLoggedUser() : string{
    return localStorage.getItem('user_id');
  }

}
