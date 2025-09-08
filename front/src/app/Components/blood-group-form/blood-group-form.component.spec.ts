import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodGroupFormComponent } from './blood-group-form.component';

describe('BloodGroupFormComponent', () => {
  let component: BloodGroupFormComponent;
  let fixture: ComponentFixture<BloodGroupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BloodGroupFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BloodGroupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
