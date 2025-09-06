import { TestBed } from '@angular/core/testing';

import { BacteriologyService } from './bacteriology.service';

describe('BacteriologyService', () => {
  let service: BacteriologyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BacteriologyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
