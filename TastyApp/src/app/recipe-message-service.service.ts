import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeMessageServiceService {

  public recipeDetails=new BehaviorSubject<string>('{ "name": "Mintaj z porem", "level": "Średni", "time": "30 min", "portions": "4", "steps": [ "umyć i osuszyć mintaja", "włożyć do piekarnika rozgrzanego do 180 stopni na 20 minut", "opruszyć pieprzem i solą, polać sokiem z cytryny" ], "ingredients": [ "ryż 100g", "cytryna", "sól i pieprz", "mintaj 20dag" ] }');

  constructor() { }

  getRecipeDetails(): Observable<string> {
  //  return new BehaviorSubject<string>('');
    return this.recipeDetails.asObservable();
  }

  updateRecipeDetails(details: string){
    this.recipeDetails.next(details);
  }


}
