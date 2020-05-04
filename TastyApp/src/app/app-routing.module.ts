import { PageNotFoundComponentComponent } from './page-not-found-component/page-not-found-component.component';
import { RecipeSummaryComponent } from './recipe-summary/recipe-summary.component';
import { AppComponent } from './app.component';
import { RecipeFormComponent } from './recipe-form/recipe-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path: 'addRecipe',
    component: RecipeFormComponent},
  {path: 'recipeSummary',
    component: RecipeSummaryComponent},
  { path: '',   redirectTo: '/addRecipe', pathMatch: 'full' },
  {path: '**', component: PageNotFoundComponentComponent, }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
