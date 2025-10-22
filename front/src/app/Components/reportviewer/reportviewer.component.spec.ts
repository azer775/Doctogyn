import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportviewerComponent } from './reportviewer.component';

describe('ReportviewerComponent', () => {
  let component: ReportviewerComponent;
  let fixture: ComponentFixture<ReportviewerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportviewerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ReportviewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
