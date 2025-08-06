import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalBackgroundFormComponent } from './medical-background-form.component';

describe('MedicalBackgroundFormComponent', () => {
  let component: MedicalBackgroundFormComponent;
  let fixture: ComponentFixture<MedicalBackgroundFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalBackgroundFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MedicalBackgroundFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
