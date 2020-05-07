import { RecipeService } from './../recipe.service';
import { RecipesOverviewModel } from './../models/recipes-overview-model';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-recipes-listing',
  templateUrl: './recipes-listing.component.html',
  styles: [
  ]
})
export class RecipesListingComponent implements OnInit {

  recipesOverviewModel : RecipesOverviewModel;
  recipesOverviewObservable: Observable<RecipesOverviewModel>;

  constructor(private recipeService: RecipeService,private route: ActivatedRoute,private router: Router) { }

  ngOnInit(): void {
    this.recipesOverviewModel = new RecipesOverviewModel();
    this.recipesOverviewObservable=this.recipeService.getRecipesOverview();
     
    this.recipesOverviewObservable.subscribe(
      message=> {this.recipesOverviewModel=message;},
      error=> console.log(error)
    );

  }

  goToDetails(id:number): void{
    this.router.navigate(['./'+id],{relativeTo: this.route});
  }


}
