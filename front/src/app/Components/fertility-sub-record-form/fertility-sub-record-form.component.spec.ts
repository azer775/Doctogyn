import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FertilitySubRecordFormComponent } from './fertility-sub-record-form.component';

describe('FertilitySubRecordFormComponent', () => {
  let component: FertilitySubRecordFormComponent;
  let fixture: ComponentFixture<FertilitySubRecordFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FertilitySubRecordFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FertilitySubRecordFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
