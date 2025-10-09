import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Scheduler2Component } from './scheduler2.component';

describe('Scheduler2Component', () => {
  let component: Scheduler2Component;
  let fixture: ComponentFixture<Scheduler2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Scheduler2Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Scheduler2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
