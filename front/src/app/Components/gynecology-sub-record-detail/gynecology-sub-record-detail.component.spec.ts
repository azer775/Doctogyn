import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GynecologySubRecordDetailComponent } from './gynecology-sub-record-detail.component';

describe('GynecologySubRecordDetailComponent', () => {
  let component: GynecologySubRecordDetailComponent;
  let fixture: ComponentFixture<GynecologySubRecordDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GynecologySubRecordDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GynecologySubRecordDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
