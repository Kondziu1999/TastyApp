import { RecipeService } from './../recipe.service';
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

  constructor(private route:ActivatedRoute,private recipeService: RecipeService,private router:Router) {
   }

  ngOnInit(): void {
    this.recipeObserver=this.route.paramMap.pipe(
      switchMap(params=>{
        this.id=Number(params.get('id'))
        return this.recipeService.getRecipe(this.id? this.id : 1);
      })
    )

    this.recipeObserver.subscribe(
      message=> this.recipe=message,
      error => {}
    )
  }

  routeToNotFound():void{
    //navigate to not found
    this.router.navigate(['xddddddddddddddddddddddddddddd'],{relativeTo: this.route});
  }

}
