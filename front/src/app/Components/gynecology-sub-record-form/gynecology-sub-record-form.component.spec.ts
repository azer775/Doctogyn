import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GynecologySubRecordFormComponent } from './gynecology-sub-record-form.component';

describe('GynecologySubRecordFormComponent', () => {
  let component: GynecologySubRecordFormComponent;
  let fixture: ComponentFixture<GynecologySubRecordFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GynecologySubRecordFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GynecologySubRecordFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
