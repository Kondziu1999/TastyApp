import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeAddedSuccessfullyComponent } from './recipe-added-successfully.component';

describe('RecipeAddedSuccessfullyComponent', () => {
  let component: RecipeAddedSuccessfullyComponent;
  let fixture: ComponentFixture<RecipeAddedSuccessfullyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecipeAddedSuccessfullyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeAddedSuccessfullyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
