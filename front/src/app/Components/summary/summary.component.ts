import { ChangeDetectorRef, Component, OnInit, Inject, Optional } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, ReactiveFormsModule } from '@angular/forms';
import { Observable, catchError } from 'rxjs';
import { ResponseType } from '../../Models/enums';
import { FinalResponse } from '../../Models/FinalResponse';
import { UnrecognizedAbbreviation } from '../../Models/UnrecognizedAbbreviation';
import { AbbreviationDefinition } from '../../Models/AbbreviationDefinition';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

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
  isSubmitting: boolean = false;
  currentMedicalRecordId: number = 1;

  constructor(
    private medicalRecordService: MedicalRecordService,
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef,
    @Optional() @Inject(MAT_DIALOG_DATA) public data?: { medicalRecordId?: number }
  ) {}

  ngOnInit(): void {
    this.currentMedicalRecordId = this.data?.medicalRecordId ?? 1;
    this.getResumeData(this.currentMedicalRecordId);
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

  submitAbbreviations(): void {
    if (!this.finalResponse?.unrecognizedAbbreviation) {
      return;
    }

    // Create AbbreviationDefinition array from forms
    const abbreviationDefinitions: AbbreviationDefinition[] = this.finalResponse.unrecognizedAbbreviation.map((abbrev, index) => {
      const meaning = this.abbreviationForms[index].get('meaning')?.value;
      return {
        abbreviation: abbrev.abbreviation,
        meaning: meaning || '' // Use empty string if no meaning provided
      };
    });

    // Check if all meanings are filled
    const hasEmptyMeanings = abbreviationDefinitions.some(def => !def.meaning || def.meaning.trim() === '');
    if (hasEmptyMeanings) {
      alert('Please provide meanings for all abbreviations before submitting.');
      return;
    }

    // Call the service with abbreviations
    this.isSubmitting = true;
    this.medicalRecordService.getResumewithAbbreviation(this.currentMedicalRecordId, abbreviationDefinitions).subscribe({
      next: (response: FinalResponse) => {
        this.finalResponse = response;
        this.abbreviationForms = []; // Clear forms
        this.isSubmitting = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error submitting abbreviations:', error);
        alert('An error occurred while submitting abbreviations. Please try again.');
        this.isSubmitting = false;
        this.cdr.detectChanges();
      }
    });
  }

  allFormsValid(): boolean {
    if (!this.abbreviationForms || this.abbreviationForms.length === 0) {
      return false;
    }
    return this.abbreviationForms.every(form => {
      const meaning = form.get('meaning')?.value;
      return meaning && meaning.trim() !== '';
    });
  }
}