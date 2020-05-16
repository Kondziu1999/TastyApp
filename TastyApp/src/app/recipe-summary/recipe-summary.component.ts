import { RecipeService } from '../services/recipe.service';
import { RecipeModel } from './../models/recipe-model.model';
import { RecipeMessageServiceService } from '../services/recipe-message-service.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-recipe-summary',
  templateUrl: './recipe-summary.component.html',
  styles: [
  ]
})
export class RecipeSummaryComponent implements OnInit, OnDestroy {

  currentRecipeDetails: RecipeModel;
  private detailsSubscription : Subscription;
  public whaitForServerResponse: boolean;
  public responseError: boolean;

  constructor(private route: ActivatedRoute,private details:RecipeMessageServiceService,private router:Router,
    private recipeService: RecipeService) {
  }

  ngOnInit(): void {
    this.detailsSubscription=this.details.getRecipeDetails()
          .subscribe(message=>this.currentRecipeDetails=JSON.parse(message));
    this.whaitForServerResponse=false;
    this.responseError=false;

  }
  ngOnDestroy(){
    this.detailsSubscription.unsubscribe();
  }

  edit(){
    this.details.updateRecipeDetails(
      JSON.stringify(this.currentRecipeDetails)
      );
      this.router.navigate(['../addRecipe'],{relativeTo: this.route})
  }

  confirm(){
    this.whaitForServerResponse=true;
    this.recipeService.addRecipe(this.currentRecipeDetails)
      .subscribe(
        message=>{this.whaitForServerResponse=false; this.navigateToSuccessSide()},
        err => {this.responseError=true; this.whaitForServerResponse=false}
        )
  }

  navigateToSuccessSide(): void{
    this.router.navigate(['../successfullyAdded'],{relativeTo: this.route});
  }  

  

}
