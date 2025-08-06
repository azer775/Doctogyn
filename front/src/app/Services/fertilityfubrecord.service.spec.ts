import { TestBed } from '@angular/core/testing';

import { FertilityfubrecordService } from './fertilityfubrecord.service';

describe('FertilityfubrecordService', () => {
  let service: FertilityfubrecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FertilityfubrecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
