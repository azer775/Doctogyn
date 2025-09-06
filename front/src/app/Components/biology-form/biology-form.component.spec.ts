import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiologyFormComponent } from './biology-form.component';

describe('BiologyFormComponent', () => {
  let component: BiologyFormComponent;
  let fixture: ComponentFixture<BiologyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BiologyFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BiologyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
