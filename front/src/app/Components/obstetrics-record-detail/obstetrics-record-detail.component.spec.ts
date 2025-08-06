import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObstetricsRecordDetailComponent } from './obstetrics-record-detail.component';

describe('ObstetricsRecordDetailComponent', () => {
  let component: ObstetricsRecordDetailComponent;
  let fixture: ComponentFixture<ObstetricsRecordDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ObstetricsRecordDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ObstetricsRecordDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
