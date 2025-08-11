import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EchographieFormComponent } from './echographie-form.component';

describe('EchographieFormComponent', () => {
  let component: EchographieFormComponent;
  let fixture: ComponentFixture<EchographieFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EchographieFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EchographieFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
