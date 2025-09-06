import { TestBed } from '@angular/core/testing';

import { SpermAnalysisService } from './sperm-analysis.service';

describe('SpermAnalysisService', () => {
  let service: SpermAnalysisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpermAnalysisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
