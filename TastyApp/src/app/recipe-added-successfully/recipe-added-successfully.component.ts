import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-recipe-added-successfully',
  templateUrl: './recipe-added-successfully.component.html',
  styles: [
  ]
})
export class RecipeAddedSuccessfullyComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
  }

  backToMenu(): void{
    this.router.navigate(['../addRecipe'],{relativeTo: this.route});
  }


}
