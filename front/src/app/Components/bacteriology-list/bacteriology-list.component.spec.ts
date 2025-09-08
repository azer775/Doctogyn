import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BacteriologyListComponent } from './bacteriology-list.component';

describe('BacteriologyListComponent', () => {
  let component: BacteriologyListComponent;
  let fixture: ComponentFixture<BacteriologyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BacteriologyListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BacteriologyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
