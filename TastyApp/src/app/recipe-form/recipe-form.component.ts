import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormArray } from '@angular/forms';
import { emptyIngredientValidator, notRequiredLengthOfStepValidator } from '../validation/recipe-from-validators';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styles: [
  ]
})
export class RecipeFormComponent implements OnInit {
  //name: any;
  numberOfSteps: number=0;
  recipeForm: any;

  constructor(private fb: FormBuilder) { }
  
  ngOnInit(): void {
    //this.name= new FormControl('');

    this.recipeForm=this.fb.group({
      name: ['',[Validators.minLength(4),Validators.required]],
      level: ['',[Validators.required]],
      time: ['',[Validators.required]],
      portions: ['',[Validators.required]],
      steps: this.fb.array([
        this.fb.control('',[notRequiredLengthOfStepValidator()])
      ],[Validators.required]),
      ingredients: this.fb.array([
        this.fb.control('',[emptyIngredientValidator()])
      ],[Validators.required]) 
    });

  }

  onSubmit(){
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


  addStep(){
    this.steps.push(this.fb.control('',[notRequiredLengthOfStepValidator()]));
    this.numberOfSteps++;
  }

  delStep(i: number){
    this.steps.removeAt(i);
    this.numberOfSteps--;
  }

  get ingredients(){
    return this.recipeForm.get('ingredients') as FormArray;
  }

  addIngredient(){
    this.ingredients.push(this.fb.control('',[emptyIngredientValidator()]));
  }
  
  delIngredient(num: number){
    this.ingredients.removeAt(num);
  }

  getStepValidity(i: number): boolean{
    return (<FormArray>this.recipeForm.get('steps')).controls[i].invalid
  }

}
