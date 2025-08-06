import { TestBed } from '@angular/core/testing';

import { ObstetricsRecordService } from './obstetrics-record.service';

describe('ObstetricsRecordService', () => {
  let service: ObstetricsRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObstetricsRecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
