import { TestBed } from '@angular/core/testing';

import { RecipeMessageServiceService } from './recipe-message-service.service';

describe('RecipeMessageServiceService', () => {
  let service: RecipeMessageServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecipeMessageServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
