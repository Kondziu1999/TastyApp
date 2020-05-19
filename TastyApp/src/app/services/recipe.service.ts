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

  backendUrl="http://localhost:8080/api"
  recipesOverviewUrlPostfix='/recipes/overview'
  constructor(private http: HttpClient) { }
  
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept': 'application/json'
      
    })
  };

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
  uploadPhotos(file: File){
    const uploadImageData  = new FormData();
    uploadImageData.append('imageFile', file, file.name);

    this.http.post<any>(this.backendUrl+"/files/images/1/1",uploadImageData)
      .subscribe(
        msg => console.log(msg),
        error => console.log(error)
      );
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
