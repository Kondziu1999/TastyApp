import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRoute, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Route } from '@angular/compiler/src/core';
import * as moment from 'moment';

@Injectable({providedIn: 'root'})
export class AuthGuard  implements CanActivate{

    constructor(private route: ActivatedRoute, private router: Router,private authService: AuthService){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
        
        // const expiration=JSON.parse(localStorage.getItem('expires_at'));
        // if(moment().isBefore(expiration)){
        //     return true;
        // }
        if(this.authService.isLoggedIn().value){
            return true;
        }


        this.router.navigate(['\login'],{queryParams : {returnUrl: state.url}});
        return false;
    }
}
