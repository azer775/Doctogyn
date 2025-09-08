import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SerologyFormComponent } from './serology-form.component';

describe('SerologyFormComponent', () => {
  let component: SerologyFormComponent;
  let fixture: ComponentFixture<SerologyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SerologyFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SerologyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
