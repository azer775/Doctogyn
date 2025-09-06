import { TestBed } from '@angular/core/testing';

import { BiologyService } from './biology.service';

describe('BiologyService', () => {
  let service: BiologyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BiologyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
