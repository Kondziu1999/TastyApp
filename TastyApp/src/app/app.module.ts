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

@NgModule({
  declarations: [
    AppComponent,
    RecipeFormComponent,
    RecipeSummaryComponent,
    PageNotFoundComponentComponent
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
