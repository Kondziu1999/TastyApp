import { RecipeModel } from './../models/recipe-model.model';
import { RecipeMessageServiceService } from './../recipe-message-service.service';
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
  

  constructor(private route: ActivatedRoute,private details:RecipeMessageServiceService,private router:Router) {
  }

  ngOnInit(): void {
    this.detailsSubscription=this.details.getRecipeDetails()
          .subscribe(message=>this.currentRecipeDetails=JSON.parse(message));
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

  }

  

}
