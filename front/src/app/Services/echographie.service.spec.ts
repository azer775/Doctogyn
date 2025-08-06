import { TestBed } from '@angular/core/testing';

import { EchographieService } from './echographie.service';

describe('EchographieService', () => {
  let service: EchographieService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EchographieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
