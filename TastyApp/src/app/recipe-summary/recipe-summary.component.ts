import { AuthService } from './../services/auth.service';
import { RecipeService } from '../services/recipe.service';
import { RecipeModel } from './../models/recipe-model.model';
import { RecipeMessageServiceService } from '../services/recipe-message-service.service';
import { Component, OnInit, OnDestroy, AfterContentChecked } from '@angular/core';
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
  public photosUploaded: boolean;
  public responseError: boolean;
  public files: Array<File>;
  public imageURL: any;

  constructor(private route: ActivatedRoute,private details:RecipeMessageServiceService,private router:Router,
    private recipeService: RecipeService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.detailsSubscription=this.details.getRecipeDetails()
          .subscribe(message=>this.currentRecipeDetails=JSON.parse(message));
    this.filesSubscription=this.details.getFiles()
          .subscribe(message => this.files=message);
     
    this.waitForRecipeUpload=false;
    this.responseError=false;
 
  }
 

  ifPhotosUploaded() : boolean{
    if(this.files== null) return true;
    if(this.photosUploaded == null) return true;
    return this.photosUploaded;
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
    //set flag to spinner
    this.waitForRecipeUpload=true;
    //set id to form
    this.currentRecipeDetails.userId = parseInt(this.authService.getIdOfLoggedUser());
    //send recipe and invoke upload photos when positive response arrives
    this.recipeService.addRecipe(this.currentRecipeDetails)
      .subscribe(
        message=>{this.waitForRecipeUpload=false;
          this.currentRecipeDetails.userId = message.userId;
          this.currentRecipeDetails.recipeId = message.recipeId; 
          console.log(this.currentRecipeDetails);
          //check if not null for security
          if(this.files == null) this.navigateToSuccessSide;
          //else if tere is image to upload 
          this.files.length > 0 ? this.whenRecipeAddedUploadPhotos() : this.navigateToSuccessSide();
        },
        err => {this.responseError=true; this.waitForRecipeUpload=false}
        )

    
  }

  whenRecipeAddedUploadPhotos(){
    this.recipeService.uploadPhotos(this.files,this.currentRecipeDetails.userId,this.currentRecipeDetails.recipeId)
        .subscribe(
          msg => {
            this.photosUploaded=true;
            if(! this.waitForRecipeUpload) this.navigateToSuccessSide();},
            err => {this.responseError=true; this.waitForRecipeUpload=false});
          
  }

  navigateToSuccessSide(): void{
    this.router.navigate(['../successfullyAdded'],{relativeTo: this.route});
  }  

  displayModalVar: boolean= false;

  displayModal(): void{
    this.displayModalVar=true;
   
  }

  closeModal(){
    this.displayModalVar=false;
  }
  
  loadImageUrl(file: File,index: number){
    let mimeType= file.type;
    if (mimeType.match(/image\/*/) == null) {
      return;
    }

    let reader = new FileReader();
    reader.readAsDataURL(file); 
    reader.onload = (_event) => { 
      // this.imageURL = reader.result; 
      this.imagesURL[index] = reader.result.toString();
       
    }
  }

  imagesURL: String[]=[];

}
