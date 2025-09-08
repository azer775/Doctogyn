import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpermAnalysisFormComponent } from './sperm-analysis-form.component';

describe('SpermAnalysisFormComponent', () => {
  let component: SpermAnalysisFormComponent;
  let fixture: ComponentFixture<SpermAnalysisFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpermAnalysisFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpermAnalysisFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
