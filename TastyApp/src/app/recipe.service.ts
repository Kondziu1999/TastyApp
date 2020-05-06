import { RecipeModel } from './models/recipe-model.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  backendUrl="http://localhost:8080"
  constructor(private http: HttpClient) { }
  
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Accept': 'application/json'
      
    })
  };

  addRecipe(recipe:RecipeModel): Observable<RecipeModel>{
    return this.http.post<RecipeModel>(this.backendUrl+'/recipes',recipe,this.httpOptions)
        .pipe(
          retry(2),
          catchError(this.handleError)
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
