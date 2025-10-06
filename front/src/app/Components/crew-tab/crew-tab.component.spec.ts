import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrewTabComponent } from './crew-tab.component';

describe('CrewTabComponent', () => {
  let component: CrewTabComponent;
  let fixture: ComponentFixture<CrewTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrewTabComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CrewTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
