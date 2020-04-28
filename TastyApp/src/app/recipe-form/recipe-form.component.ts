import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormArray } from '@angular/forms';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.component.html',
  styles: [
  ]
})
export class RecipeFormComponent implements OnInit {
  name: any;
  numberOfSteps: number=0;
  recipeForm: any;

  constructor(private fb: FormBuilder) { }
  
  ngOnInit(): void {
    this.name= new FormControl('');

    this.recipeForm=this.fb.group({
      name: [''],
      level: [''],
      time: [''],
      portions: [''],
      steps: this.fb.array([
        this.fb.control('')
      ]),
      ingredients: this.fb.array([
        this.fb.control('')
      ]) 
    });

  }

  onSubmit(){
  }
  get steps(): FormArray{
    return this.recipeForm.get('steps') as FormArray;
  }

  addStep(){
    this.steps.push(this.fb.control(''));
    this.numberOfSteps++;
  }

  delStep(){
    this.steps.removeAt(this.numberOfSteps)
    this.numberOfSteps--
  }

  get ingredients(){
    return this.recipeForm.get('ingredients') as FormArray;
  }

  addIngredient(){
    this.ingredients.push(this.fb.control(''));
  }
  
  delIngredient(num: number){
    this.ingredients.removeAt(num);
  }


}
