import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ObstetricsRecordFormComponent } from './obstetrics-record-form.component';

describe('ObstetricsRecordFormComponent', () => {
  let component: ObstetricsRecordFormComponent;
  let fixture: ComponentFixture<ObstetricsRecordFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ObstetricsRecordFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ObstetricsRecordFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
