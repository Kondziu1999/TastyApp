import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styles: [
  ]
})
export class NavBarComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
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

}
