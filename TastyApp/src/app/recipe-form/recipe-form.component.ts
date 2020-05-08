import { BehaviorSubject, Subscription } from 'rxjs';
import { RecipeModel } from './../models/recipe-model.model';
import { RecipeMessageServiceService } from './../recipe-message-service.service';
import { Component, OnInit, OnDestroy, DoCheck } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormArray } from '@angular/forms';
import { emptyIngredientValidator, notRequiredLengthOfStepValidator } from '../validation/recipe-from-validators';
import { stringify } from 'querystring';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styles: [
  ]
})
export class RecipeFormComponent implements OnInit, OnDestroy {
  //name: any;
  numberOfSteps: number=0;
  recipeForm: any;
  currentRecipeDetails: RecipeModel;
  private detailsSubscription : Subscription;
  
  constructor(private fb: FormBuilder, private details: RecipeMessageServiceService, 
          private route: ActivatedRoute,private router:Router) { }
  
  ngOnInit(): void {
    //this.name= new FormControl('');

    this.recipeForm=this.fb.group({
      name: ['',[Validators.minLength(4),Validators.required]],
      level: ['',[Validators.required]],
      time: ['',[Validators.required]],
      portions: ['',[Validators.required]],
      steps: this.fb.array([
        // this.fb.control('',[notRequiredLengthOfStepValidator()])
      ],[Validators.required]),
      ingredients: this.fb.array([
        // this.fb.control('',[emptyIngredientValidator()])
      ],[Validators.required]) ,
      description: ['']
    });

    //if there user whant to edit recipe again
    this.detailsSubscription=this.details.getRecipeDetails()
          .subscribe((message)=>{
            if(message.length>0){
              this.currentRecipeDetails=JSON.parse(message);
              this.updateForm();}
          });

  }
  updateForm():void{
    //fill form
    if(this.currentRecipeDetails){
       this.recipeForm.patchValue({
      name: this.currentRecipeDetails.name,
      level: this.currentRecipeDetails.level,
      time: this.currentRecipeDetails.time,
      portions: this.currentRecipeDetails.portions,
      description: this.currentRecipeDetails.description
    });

    this.currentRecipeDetails.ingredients
      .forEach(ingredient=>this.addIngredient(ingredient));
   
    this.currentRecipeDetails.steps
      .forEach(step=>this.addStep(step));
    }    
  }


  ngOnDestroy():void{
    this.detailsSubscription.unsubscribe();
  }

  onSubmit(){
    //alert(stringify(this.recipeForm));
    this.router.navigate(['../recipeSummary'],{relativeTo: this.route});
    this.details.updateRecipeDetails(this.formToJson());
  }

  formToJson():string {
    return JSON.stringify(this.recipeForm.getRawValue());
  }
  
  get level(){
    return this.recipeForm.get('level');
  }
  get time(){
    return this.recipeForm.get('time');
  }
  get portions(){
    return this.recipeForm.get('poritons');
  }

  get name(){
    return this.recipeForm.get('name');
  }
  
  get steps(): FormArray{
    return this.recipeForm.get('steps') as FormArray;
  }

  get description(){
    return this.recipeForm.get('description');
  }


  addStep(step?: string){
    this.numberOfSteps++;
    if(step){
      this.steps.push(this.fb.control(step,[notRequiredLengthOfStepValidator()]));
      return;
    }
    this.steps.push(this.fb.control('',[notRequiredLengthOfStepValidator()]));
  }
  

  delStep(i: number){
    this.steps.removeAt(i);
    this.numberOfSteps--;
  }

  get ingredients(){
    return this.recipeForm.get('ingredients') as FormArray;
  }

  addIngredient(ingredient?: string){
    if(ingredient){
      this.ingredients.push(this.fb.control(ingredient,[emptyIngredientValidator()]));
      return;
    }
    this.ingredients.push(this.fb.control('',[emptyIngredientValidator()]));
  }
  
  delIngredient(num: number){
    this.ingredients.removeAt(num);
  }

  getStepValidity(i: number): boolean{
    return (<FormArray>this.recipeForm.get('steps')).controls[i].invalid
  }
  getIngredientValidity(i: number):boolean{
    return (<FormArray>this.recipeForm.get('ingredients')).controls[i].valid;
  }

}
