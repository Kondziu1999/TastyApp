import { FormControl, Validators, FormGroup } from '@angular/forms';
import { PhotosNamesDto } from './../payload/PhotosNamesDto';
import { RecipeService } from '../services/recipe.service';
import { RecipeModel } from './../models/recipe-model.model';
import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { commentDto } from '../models/commentDto';

@Component({
  selector: 'app-recipe-overview',
  templateUrl: './recipe-overview.component.html',
  styles: [
  ]
})
export class RecipeOverviewComponent implements OnInit {

  public recipe: RecipeModel;
  private id: number;
  private recipeObserver: Observable<RecipeModel>;
  public ifContentLoading: boolean;
  public photosNamesDto: PhotosNamesDto;
  public photosUrls : Array<string>;
  public photosUrlPrefix: string;
  public comments: commentDto[];
  // public commentForm:FormControl = new FormControl('',[Validators.maxLength(100),Validators.minLength(5)]);

  public form = new FormGroup({
    'comment': new FormControl('',[Validators.minLength(5),Validators.maxLength(100)])
  });

  get comment(){
    return this.form.get('comment');
  }
  displayModalVar: boolean= false;
  imageURL : string;

  constructor(private route:ActivatedRoute,private recipeService: RecipeService,private router:Router,
    private renderer2: Renderer2,private el:ElementRef) {
   }

  
  ngOnInit(): void {
    this.ifContentLoading=true;

    this.recipeObserver=this.route.paramMap.pipe(
      switchMap(params => {
        this.id = Number(params.get('id'))
        return this.recipeService.getRecipe(this.id? this.id : 0);
      })
    )
    //load photos url prefix NOTE it is not full
    this.photosUrlPrefix = this.recipeService.getBackendPhotoUrl();

    this.recipeObserver.subscribe(
      message=> {this.recipe=message; this.ifContentLoading=false;console.log(message);
        this.getRecipeNames(); 
        this.photosUrlPrefix = this.photosUrlPrefix + this.recipe.userId + "/" + this.recipe.recipeId;
        this.getComments();
        console.log(this.photosUrlPrefix);
      },

      error => {this.routeToNotFound()}
    )

  }

  parseToUrls(){
    this.photosUrls = this.photosNamesDto.names.map( name => this.photosUrlPrefix + "/"+ name);
    console.log(this.photosUrls);
  }
  routeToNotFound():void{
    //navigate to not found
    this.router.navigate(['../../NotFound'],{relativeTo: this.route});
  }

  getRecipeNames(){
    this.recipeService.getPhotosNames(this.recipe.userId,this.id)
      .subscribe(
        message => {this.photosNamesDto= message; console.log(message); this.parseToUrls();},
        error => console.log(error)
      );
  }

  getComments(){
    this.recipeService.getRecipeComments(this.recipe.recipeId)
      .subscribe(
        message => {this.comments = message; console.log(message);this.comments},
        error => console.log(error)
      );
  }

  displayModal(): void{
    this.displayModalVar=true;
   
  }

  closeModal(){
    this.displayModalVar=false;
  }

  public expanded = false;

  expandCommentContainer(){
    this.expanded = true;
  }

  addComment(){
      console.log(this.comment.value);
  }

  checkValidity(){
    const com = (<string>this.comment.value)
    return !(com.length<5 || com.length>100);
  }
}
