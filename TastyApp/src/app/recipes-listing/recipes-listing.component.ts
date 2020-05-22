import { RecipeService } from '../services/recipe.service';
import { RecipesOverviewModel } from './../models/recipes-overview-model';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-recipes-listing',
  templateUrl: './recipes-listing.component.html',
  styles: [
  ]
})
export class RecipesListingComponent implements OnInit {

  recipesOverviewModel : RecipesOverviewModel;
  recipesOverviewObservable: Observable<RecipesOverviewModel>;
  currentPage: number;
  lastPageFlag: boolean;
  private MAX_PAGE_SIZE:number=10;
  private recipeObserver: any;

  constructor(private recipeService: RecipeService,private route: ActivatedRoute,private router: Router,
    private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.lastPageFlag=false;
    this.currentPage=0;
    this.recipesOverviewModel = new RecipesOverviewModel();

    this.recipeObserver={ 
      next: (message: RecipesOverviewModel)=> {
        this.recipesOverviewModel = message;
        this.recipesOverviewModel.recipesOverview.length < this.MAX_PAGE_SIZE? this.lastPageFlag = true : this.lastPageFlag = false
        },
      error : (error: any) => console.log(error)
    }

    this.recipesOverviewObservable=this.recipeService.getRecipesOverview();
     
    this.recipesOverviewObservable.subscribe( this.recipeObserver);

  }

  goToDetails(id:number): void{
    this.router.navigate(['./'+id],{relativeTo: this.route});
  }


  nextPage(): void{
      //if there is some page
    if(! this.lastPageFlag ){
      this.currentPage++;
      this.loadAnotherPage(this.currentPage);
    }
    console.log(this.currentPage);

  }
  previousPage(): void{
    //if it's possible to go back
    if(this.currentPage>0){
      this.currentPage--;
      this.loadAnotherPage(this.currentPage);
    }
    console.log(this.currentPage);
  }


  loadAnotherPage(pageNumber: number): void{
    this.recipesOverviewObservable = this.recipeService.getRecipesOverview(pageNumber);
    this.recipesOverviewObservable
      .subscribe(this.recipeObserver);

  }

}


