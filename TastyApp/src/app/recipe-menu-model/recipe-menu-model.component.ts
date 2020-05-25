import { RecipeService } from './../services/recipe.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-recipe-menu-model',
  templateUrl: './recipe-menu-model.component.html',
  styles: [
  ]
})
export class RecipeMenuModelComponent implements OnInit {

  //all fields are fetched previously since they are required to pagination
  @Input("userId") userId: number;
  @Input("recipeId") recipeId: number;
  @Input("name") name: string;
  @Input("level") level: string;
  @Input("time") time: string;
  @Input("portions") poritons: string;

  imageName: string;
  imageURL : string;
  onMouseOver: boolean=false;

  constructor(private recipeService: RecipeService) {
   }

  ngOnInit(): void {
    this.recipeService.getPhotosNames(this.userId,this.recipeId)
        .subscribe(
          msg => {this.imageName = msg.names[0];
                  this.imageURL = this.recipeService.getBackendURL() + "/files/images/" + this.userId + "/" + this.recipeId + "/"+this.imageName;
                console.log(this.imageURL)},
          err => console.log(err)
        )
     }

    mouseOver(){
      this.onMouseOver = true;
    }
    mouseOut(){
      this.onMouseOver = false;
    }

}
