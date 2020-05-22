import { RecipeService } from './../services/recipe.service';
import { BehaviorSubject, Subscription } from 'rxjs';
import { RecipeModel } from './../models/recipe-model.model';
import { RecipeMessageServiceService } from '../services/recipe-message-service.service';
import { Component, OnInit, OnDestroy, DoCheck, ViewChild, ElementRef } from '@angular/core';
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

  //subscriptions to service which serve details between form and summary component
  private detailsSubscription : Subscription;
  private filesSubscription: Subscription;

  files: Array<{
    'fileName': string,
    'selectedFile': File
  }> = [];

  // imageURL: any;

  // displayModalVar: boolean= false;

  // displayModal(): void{
  //   this.displayModalVar=true;
   
  // }

  // closeModal(){
  //   this.displayModalVar=false;
  // }


  constructor(private fb: FormBuilder, private details: RecipeMessageServiceService, 
      private route: ActivatedRoute,private router:Router, private recipeService: RecipeService) { }
  
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
    
    //since message service hold pure file there is need to map it back to object 
    //TODO refactor it
    this.filesSubscription=this.details.getFiles()
          .subscribe(
            msg =>{ 
              if(msg !=null) {
                this.files=msg.map(val => ({'fileName': val.name,
                'selectedFile': val}))
              }
            }
          );
          
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
    this.details.updateFiles(this.files.map(file => file.selectedFile));

    // this.files.forEach(file => {
    //   this.recipeService.uploadPhoto(file.selectedFile)
    //     .subscribe(msg => console.log(msg))}
    // );

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


  dragOverHandler(ev) {
    console.log('File(s) in drop zone'); 
  
    // Prevent default behavior (Prevent file from being opened)
    ev.preventDefault();
    ev.stopPropagation();
  }

  addFile(files: FileList){
    for ( var i=0; i< files.length; i++){
      let obj = {
        fileName: files[i].name,
        selectedFile: files[i]
      }
      if(this.files.length<4){
        this.files.push(obj);
      }
    }

  }
  dropHandler(ev){
    ev.preventDefault();

    if(ev.dataTransfer.items){
      for (var i = 0; i < ev.dataTransfer.items.length; i++) {
        
        if (ev.dataTransfer.items[i].kind === 'file') {
          let file = ev.dataTransfer.items[i].getAsFile();
          let obj = {
            fileName: file.name,
            selectedFile: file
          }
          if(this.files.length<4){
            this.files.push(obj);
          }
          console.log('... file[' + i + '].name = ' + file.name);
        }
      }
    }
    // this.preview();
  }

  deleteFile(index: number){
    console.log(this.files);

    this.files.slice(index,1);
    this.files=this.files.filter(obj => obj!== this.files[index]);
    console.log(this.files);
  }

//   preview() {
//     // Show preview 
//     var mimeType = this.files[0].selectedFile.type;
//     if (mimeType.match(/image\/*/) == null) {
//       return;
//     }
 
//     var reader = new FileReader();      
//     reader.readAsDataURL(this.files[0].selectedFile); 
//     reader.onload = (_event) => { 
//       this.imageURL = reader.result; 
//     }
// }

}
