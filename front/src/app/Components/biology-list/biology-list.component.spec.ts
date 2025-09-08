import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BiologyListComponent } from './biology-list.component';

describe('BiologyListComponent', () => {
  let component: BiologyListComponent;
  let fixture: ComponentFixture<BiologyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BiologyListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BiologyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
