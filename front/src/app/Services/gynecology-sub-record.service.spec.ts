import { TestBed } from '@angular/core/testing';

import { GynecologySubRecordService } from './gynecology-sub-record.service';

describe('GynecologySubRecordService', () => {
  let service: GynecologySubRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GynecologySubRecordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
