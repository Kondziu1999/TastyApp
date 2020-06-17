import { commentDto } from './../models/commentDto';
import { PhotosNamesDto } from './../payload/PhotosNamesDto';
import { RecipeModel } from '../models/recipe-model.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { RecipesOverviewModel } from '../models/recipes-overview-model';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  backendUrl = "http://localhost:8080/api"
  backendPhotoUrl =  "http://localhost:8080/api/files/images/";

  recipesOverviewUrlPostfix='/recipes/overview'
  constructor(private http: HttpClient) { }
  
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept': 'application/json'
      
    })
  };
  getBackendURL(){
    return this.backendUrl;
  }
  getBackendPhotoUrl(){
    return this.backendPhotoUrl;
  }

  getRecipe(id:number): Observable<RecipeModel>{
    return this.http.get<RecipeModel>(this.backendUrl+'/recipes/'+id,this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  addRecipe(recipe:RecipeModel): Observable<RecipeModel>{
    return this.http.post<RecipeModel>(this.backendUrl+'/recipes',recipe,this.httpOptions)
        .pipe(
          retry(2),
          catchError(this.handleError)
        );
  }

  getRecipesOverview(pageNumber?: number): Observable<RecipesOverviewModel>{
    let requestUrl: string=this.backendUrl+this.recipesOverviewUrlPostfix;
    pageNumber? requestUrl=(requestUrl+ "/"+pageNumber): requestUrl;

    return this.http.get<RecipesOverviewModel>(requestUrl,this.httpOptions)
      .pipe(
        retry(2),
        catchError(this.handleError)
      );
  }

  private JsonHeader=new HttpHeaders({ 'Accept': 'application/json'});

  uploadPhoto(file: File){
    const uploadImageData  = new FormData();
    uploadImageData.append('imageFile', file, file.name);

    return this.http.post<any>(this.backendUrl+"/files/images/1/1",uploadImageData)
      .pipe(retry(2));
  }

  uploadPhotos(files: Array<File>,userId: number, recipeId: number){
    const uploadImagesData  = new FormData();

    files.forEach(file => {
      uploadImagesData.append('imageFile', file, file.name);
   
    });
    
    return this.http.post<any>(this.backendUrl+"/files/images/"+userId+"/"+recipeId,uploadImagesData)
      .pipe(retry(2));
  }

  getPhoto(){
    //return image directly
    return this.http.get<Blob>(this.backendUrl+"/files/images/1/1",{responseType : "blob" as "json" });
  }
  
  getPhotosNames( userId :number , recipeId: number) : Observable<PhotosNamesDto>{
    return this.http.get<PhotosNamesDto>( this.backendUrl + "/files/images/urls/" + userId + "/" + recipeId);
  }

  getRecipeComments(recipeId: number){
    return this.http.get<commentDto[]> (this.backendUrl + "/recipes/"+recipeId+"/comments");
  }

  private handleError(error: HttpErrorResponse){
    //client side error
    if(error.error instanceof ErrorEvent){
        console.error("cliend side error");
    }
    //backend error
    else{
      console.error("server side error"+ error);
    }
    return throwError("Something bad happened, Try again later.");
  }
}
