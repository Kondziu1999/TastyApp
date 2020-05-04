import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeMessageServiceService {

  public recipeDetails=new BehaviorSubject<string>('{ "name": "asdfasdf", "level": "Średni", "time": "10 min", "portions": "1", "steps": [ "sadfsadfasdfasdf", "asdfsadfsadfasdf", "asdfasdfsadf" ], "ingredients": [ "asdfasdf", "asdfasdf", "asdfsadfasdf", "sadfsadfsad" ] }');

  constructor() { }

  getRecipeDetails(): Observable<string> {
    return this.recipeDetails.asObservable();
  }

  updateRecipeDetails(details: string){
    this.recipeDetails.next(details);
  }


}
