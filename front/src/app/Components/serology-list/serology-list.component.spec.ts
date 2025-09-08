import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SerologyListComponent } from './serology-list.component';

describe('SerologyListComponent', () => {
  let component: SerologyListComponent;
  let fixture: ComponentFixture<SerologyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SerologyListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SerologyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
