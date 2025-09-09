import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailAnalysesListComponent } from './email-analyses-list.component';

describe('EmailAnalysesListComponent', () => {
  let component: EmailAnalysesListComponent;
  let fixture: ComponentFixture<EmailAnalysesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmailAnalysesListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmailAnalysesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
