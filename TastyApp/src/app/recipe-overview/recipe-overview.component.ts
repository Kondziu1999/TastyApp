import { AuthService } from './../services/auth.service';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { PhotosNamesDto } from './../payload/PhotosNamesDto';
import { RecipeService } from '../services/recipe.service';
import { RecipeModel } from './../models/recipe-model.model';
import { Component, OnInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router, RouterStateSnapshot } from '@angular/router';
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
  public ifCommentLoading : boolean;
  public commentError: boolean;
  public commentUploaded: boolean ;


  public form = new FormGroup({
    'comment': new FormControl('',[Validators.minLength(5),Validators.maxLength(100)])
  });

  get comment(){
    return this.form.get('comment');
  }
  displayModalVar: boolean= false;
  imageURL : string;

  constructor(private route:ActivatedRoute,private recipeService: RecipeService,private router:Router,
    private auth: AuthService) {
   }

  
  ngOnInit(): void {
    this.ifContentLoading = true;
    this.ifCommentLoading = false;
    this.commentError = false;
    this.commentUploaded = false;

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
    console.log("if user is logged out:" + this.auth.isLoggedOut())
    this.router.navigate(["/recipes/"+this.recipe.recipeId+"/addComment"]);
   // if( this.auth.isLoggedOut() ) this.router.navigateByUrl("/login");
    this.expanded = true;
  }
  addComment(){
      const comment = new commentDto(this.comment.value);
      this.ifCommentLoading = true;
      this.recipeService.uploadComment(comment, this.recipe.recipeId)
        .subscribe(
            message => {this.ifContentLoading = false, this.commentUploaded = true,this.ifCommentLoading = false},
            error => {this.ifCommentLoading = false, this.commentError = true}
        );
  }

  checkValidity(){
    const com = (<string>this.comment.value)
    return !(com.length<5 || com.length>100);
  }
}
