import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacteriologyFormComponent } from './bacteriology-form.component';

describe('BacteriologyFormComponent', () => {
  let component: BacteriologyFormComponent;
  let fixture: ComponentFixture<BacteriologyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacteriologyFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacteriologyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
