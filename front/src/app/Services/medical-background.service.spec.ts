import { TestBed } from '@angular/core/testing';

import { MedicalBackgroundService } from './medical-background.service';

describe('MedicalBackgroundService', () => {
  let service: MedicalBackgroundService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MedicalBackgroundService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
