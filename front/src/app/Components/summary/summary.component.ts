import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, ReactiveFormsModule } from '@angular/forms';
import { Observable, catchError } from 'rxjs';
import { ResponseType } from '../../Models/enums';
import { FinalResponse } from '../../Models/FinalResponse';
import { UnrecognizedAbbreviation } from '../../Models/UnrecognizedAbbreviation';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-summary',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './summary.component.html',
  styleUrl: './summary.component.css'
})
export class SummaryComponent implements OnInit {
  finalResponse: FinalResponse | null = null;
  ResponseType = ResponseType;
  abbreviationForms: FormGroup[] = [];
  isLoading: boolean = true;

  constructor(
    private medicalRecordService: MedicalRecordService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.getResumeData(1); // Assuming ID 1 for example
  }

  getResumeData(id: number): void {
    this.isLoading = true;
    this.medicalRecordService.getResume(id).subscribe({
      next: (response: FinalResponse) => {
        this.finalResponse = response;
        if (response.responseType === ResponseType.ABBREVIATION_ISSUE && response.unrecognizedAbbreviation && response.unrecognizedAbbreviation.length > 0) {
          this.createAbbreviationForms(response.unrecognizedAbbreviation);
        }
        this.isLoading = false;
        this.cdr.detectChanges(); // Trigger change detection
      },
      error: (error) => {
        console.error('Error fetching resume:', error);
        this.isLoading = false;
        this.cdr.detectChanges(); // Trigger change detection on error
      }
    });
  }

  createAbbreviationForms(abbreviations: UnrecognizedAbbreviation[]): void {
    this.abbreviationForms = abbreviations.map(() => 
      this.fb.group({
        meaning: ['']
      })
    );
  }

  getAbbreviationForm(index: number): FormGroup {
    return this.abbreviationForms[index];
  }

  setMeaning(index: number, meaning: string): void {
    this.abbreviationForms[index].get('meaning')?.setValue(meaning);
  }
}