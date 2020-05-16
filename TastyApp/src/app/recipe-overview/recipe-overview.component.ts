import { RecipeService } from '../services/recipe.service';
import { RecipeModel } from './../models/recipe-model.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';

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

  constructor(private route:ActivatedRoute,private recipeService: RecipeService,private router:Router) {
   }

  ngOnInit(): void {
    this.ifContentLoading=true;

    this.recipeObserver=this.route.paramMap.pipe(
      switchMap(params=>{
        this.id=Number(params.get('id'))
        return this.recipeService.getRecipe(this.id? this.id : 0);
      })
    )

    this.recipeObserver.subscribe(
      message=> {this.recipe=message; this.ifContentLoading=false},
      error => {this.routeToNotFound()}
    )
  }

  routeToNotFound():void{
    //navigate to not found
    this.router.navigate(['../../NotFound'],{relativeTo: this.route});
  }

}
