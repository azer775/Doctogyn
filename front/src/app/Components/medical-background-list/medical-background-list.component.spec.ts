import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicalBackgroundListComponent } from './medical-background-list.component';

describe('MedicalBackgroundListComponent', () => {
  let component: MedicalBackgroundListComponent;
  let fixture: ComponentFixture<MedicalBackgroundListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MedicalBackgroundListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MedicalBackgroundListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
