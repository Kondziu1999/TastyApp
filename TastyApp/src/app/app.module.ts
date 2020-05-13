import { RecipeService } from './recipe.service';
import { RecipeMessageServiceService } from './recipe-message-service.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecipeFormComponent } from './recipe-form/recipe-form.component';
import { RecipeSummaryComponent } from './recipe-summary/recipe-summary.component';
import { PageNotFoundComponentComponent } from './page-not-found-component/page-not-found-component.component';
import { HttpClientModule } from '@angular/common/http';
import { RecipeAddedSuccessfullyComponent } from './recipe-added-successfully/recipe-added-successfully.component';
import { RecipeOverviewComponent } from './recipe-overview/recipe-overview.component';
import { RecipesListingComponent } from './recipes-listing/recipes-listing.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { LoginComponent } from './login/login.component';
import { RegistrationFormComponent } from './registration-form/registration-form.component';
import { RegistrationSuccessComponent } from './registration-success/registration-success.component';

@NgModule({
  declarations: [
    AppComponent,
    RecipeFormComponent,
    RecipeSummaryComponent,
    PageNotFoundComponentComponent,
    RecipeAddedSuccessfullyComponent,
    RecipeOverviewComponent,
    RecipesListingComponent,
    NavBarComponent,
    LoginComponent,
    RegistrationFormComponent,
    RegistrationSuccessComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [RecipeMessageServiceService,RecipeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
