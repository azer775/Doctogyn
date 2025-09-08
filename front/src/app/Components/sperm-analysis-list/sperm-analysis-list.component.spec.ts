import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpermAnalysisListComponent } from './sperm-analysis-list.component';

describe('SpermAnalysisListComponent', () => {
  let component: SpermAnalysisListComponent;
  let fixture: ComponentFixture<SpermAnalysisListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpermAnalysisListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpermAnalysisListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
