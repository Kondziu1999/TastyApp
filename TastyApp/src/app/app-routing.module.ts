import { ResetPasswordFormComponent } from './reset-password-form/reset-password-form.component';
import { ResetPasswordComponentComponent } from './reset-password-component/reset-password-component.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';
import { RegistrationFormComponent } from './registration-form/registration-form.component';
import { AuthGuard } from './auth-guard';
import { LoginComponent } from './login/login.component';
import { RecipesListingComponent } from './recipes-listing/recipes-listing.component';
import { RecipeOverviewComponent } from './recipe-overview/recipe-overview.component';
import { RecipeAddedSuccessfullyComponent } from './recipe-added-successfully/recipe-added-successfully.component';
import { PageNotFoundComponentComponent } from './page-not-found-component/page-not-found-component.component';
import { RecipeSummaryComponent } from './recipe-summary/recipe-summary.component';
import { AppComponent } from './app.component';
import { RecipeFormComponent } from './recipe-form/recipe-form.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {path: 'addRecipe',
    component: RecipeFormComponent, canActivate: [AuthGuard]},
  {path: 'recipeSummary',
    component: RecipeSummaryComponent, canActivate: [AuthGuard]},
  { path: 'successfullyAdded', 
    component: RecipeAddedSuccessfullyComponent},
  // { path: 'recipeOverview', 
  //   component: RecipeOverviewComponent},
  {path: 'recipes', children:[
    {
      path: '',
      component: RecipesListingComponent
    },
    {
      path: ':id',
      component: RecipeOverviewComponent
    }
  ]},
  {path: 'register',component: RegistrationFormComponent},
  {path: 'registrationSuccess',component: RegistrationSuccessComponent},
  {path: 'login',component: LoginComponent ,children:[
    {
      path: 'resetPassword',
      component: ResetPasswordComponentComponent
  }]},
  {path: 'resetPassword',children:[{
    path: ':token',
    component: ResetPasswordFormComponent
  },{
    path: '',
    redirectTo: '/NotFound',pathMatch: 'full'
  }]},
  {path: 'NotFound', component: PageNotFoundComponentComponent},
  { path: '',   redirectTo: '/recipes', pathMatch: 'full' },
  {path: '**', component: PageNotFoundComponentComponent, }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
