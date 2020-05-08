import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RecipeMessageServiceService {

  public recipeDetails=new BehaviorSubject<string>('{  "name": "Sałatka z avocado", "level": "Łatwy", "time": "30 min", "portions": "4", "steps": [ "Pierś kurczaka pokrój w kostkę, posyp Przyprawą do złotego kurczaka Knorr i usmaż na patelni. Następnie wystudź.", "Pokrój w kostkę pomidora oraz boczek, który następnie wysmaż na patelni. Odlej tłuszcz.", "Awokado pokrój w kostkę i wymieszaj z sokiem z cytryny. Ser zetrzyj na tarce.", "Sałatę pokrój na mniejsze kawałki, po czym wymieszaj w misce ze wszystkimi przygotowanymi składnikami oraz sosem. Sałatkę podawaj posypaną grzankami.", "Przygotuj sos sałatkowy. Wymieszaj zawartość opakowania Knorr z majonezem i startym serem." ], "ingredients": [ "Sos sałatkowy czosnkowy Knorr 1 opakowanie", "Majonez Hellmanns Babuni 3 łyżki", "Przyprawa do złotego kurczaka Knorr", "pierś z kurczaka 200 gramów", "wędzony boczek 100 gramów", "dojrzałe awokado 2 sztuki", "sałata rzymska 300 gramów" ], "description": "superancka trzeba wyprobowac" }');

  constructor() { }

  getRecipeDetails(): Observable<string> {
  //  return new BehaviorSubject<string>('');
    return this.recipeDetails.asObservable();
  }

  updateRecipeDetails(details: string){
    this.recipeDetails.next(details);
  }


}
