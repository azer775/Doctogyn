import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalRecordPreviewComponent } from './medical-record-preview.component';

describe('MedicalRecordPreviewComponent', () => {
  let component: MedicalRecordPreviewComponent;
  let fixture: ComponentFixture<MedicalRecordPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalRecordPreviewComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MedicalRecordPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
