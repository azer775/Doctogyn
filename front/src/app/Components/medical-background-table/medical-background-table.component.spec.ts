import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalBackgroundTableComponent } from './medical-background-table.component';

describe('MedicalBackgroundTableComponent', () => {
  let component: MedicalBackgroundTableComponent;
  let fixture: ComponentFixture<MedicalBackgroundTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalBackgroundTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MedicalBackgroundTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
