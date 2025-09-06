import { TestBed } from '@angular/core/testing';

import { SerologyService } from './serology.service';

describe('SerologyService', () => {
  let service: SerologyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SerologyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
