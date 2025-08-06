import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FertilitySubRecordDetailComponent } from './fertility-sub-record-detail.component';

describe('FertilitySubRecordDetailComponent', () => {
  let component: FertilitySubRecordDetailComponent;
  let fixture: ComponentFixture<FertilitySubRecordDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FertilitySubRecordDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FertilitySubRecordDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
