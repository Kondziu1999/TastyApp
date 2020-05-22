import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeMenuModelComponent } from './recipe-menu-model.component';

describe('RecipeMenuModelComponent', () => {
  let component: RecipeMenuModelComponent;
  let fixture: ComponentFixture<RecipeMenuModelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecipeMenuModelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeMenuModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
