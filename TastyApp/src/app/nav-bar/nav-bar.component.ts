import { AuthService } from '../services/auth.service';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styles: [
  ]
})
export class NavBarComponent implements OnInit {

  isUserLoggedIn: boolean;
  public isToogle:boolean=false;

  constructor(private route: ActivatedRoute,private router: Router,private authService: AuthService) { 
  }

  ngOnInit(): void {
    this.isUserLoggedIn=this.authService.isLoggedIn().value;
    this.authService.isLoggedIn()
      .subscribe(msg => {
        this.isUserLoggedIn=msg;
        console.log("inside navbar" + msg)});
  }

  goToRecipes(): void{
    this.router.navigate(['recipes']);

  }

  goToAddRecipe(): void{
    this.router.navigate(['addRecipe']);
  }

  goToHomePage():void{
    this.goToRecipes();
  }

  changeToogle(): void{
    this.isToogle=!this.isToogle;
  }

  logout(){
    this.authService.logout();
  }

}
