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
  private filesSubscription: Subscription;
  public waitForRecipeUpload: boolean;
  public photosUploaded: number;
  public responseError: boolean;
  public files: Array<File>;

  constructor(private route: ActivatedRoute,private details:RecipeMessageServiceService,private router:Router,
    private recipeService: RecipeService) {
  }

  ngOnInit(): void {
    this.detailsSubscription=this.details.getRecipeDetails()
          .subscribe(message=>this.currentRecipeDetails=JSON.parse(message));
    this.filesSubscription=this.details.getFiles()
          .subscribe(message => this.files=message);

    this.waitForRecipeUpload=false;
    this.responseError=false;
    this.photosUploaded=0;

  }

  ifPhotosUploaded() : boolean{
    if(this.files== null) return true;
    return this.files.length < this.photosUploaded ? false : true;
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
    this.waitForRecipeUpload=true;
    

    this.recipeService.addRecipe(this.currentRecipeDetails)
      .subscribe(
        message=>{this.waitForRecipeUpload=false; 
          if(this.photosUploaded) this.navigateToSuccessSide()
        },
        err => {this.responseError=true; this.waitForRecipeUpload=false}
        )
    if(this.files !=null){
      this.files.forEach(file => this.recipeService.uploadPhoto(file)
          .subscribe(
            msg => {this.photosUploaded+=1;
              if(this.photosUploaded && ! this.waitForRecipeUpload) this.navigateToSuccessSide()},
            err => this.responseError=true));
    }
  }

  navigateToSuccessSide(): void{
    this.router.navigate(['../successfullyAdded'],{relativeTo: this.route});
  }  

  

}
