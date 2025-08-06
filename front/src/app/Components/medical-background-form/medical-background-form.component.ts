import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators ,ReactiveFormsModule} from '@angular/forms';
import { FamilialPathology, Allergies, MedicalPathology, ChirurgicalPathology, BackgroundType } from '../../Models/enums';
import { MedicalBackground } from '../../Models/MedicalBackground';
import { MedicalBackgroundService } from '../../Services/medical-background.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-medical-background-form',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './medical-background-form.component.html',
  styleUrl: './medical-background-form.component.css',
  providers: [MedicalBackgroundService]
})
export class MedicalBackgroundFormComponent implements OnInit {
  medicalBackgroundForm: FormGroup;
  familialPathologies = Object.values(FamilialPathology);
  allergies = Object.values(Allergies);
  medicalPathologies = Object.values(MedicalPathology);
  chirurgicalPathologies = Object.values(ChirurgicalPathology);
  @Input() data: { medicalRecordId: number; backgroundType: string } = { medicalRecordId: 1, backgroundType: 'Familial' };
  @Input() medicalBackground: MedicalBackground | null = null;
  @Output() formDeleted = new EventEmitter<void>();

  constructor(private fb: FormBuilder) {
    this.medicalBackgroundForm = this.fb.group({
      familialPathology: [FamilialPathology.OVARIAN_CANCER],
      allergies: [Allergies.NONE],
      medicalPathology: [MedicalPathology.HEART_DISEASE],
      chirurgicalPathology: [ChirurgicalPathology.APPENDECTOMY],
      comment: [''],
      date: [null]
    });
  }

  ngOnInit(): void {
    this.updateFormValidators(this.data.backgroundType);
    if (this.medicalBackground) {
      this.medicalBackgroundForm.patchValue({
        familialPathology: this.medicalBackground.familialPathology || FamilialPathology.OVARIAN_CANCER,
        allergies: this.medicalBackground.allergies || Allergies.NONE,
        medicalPathology: this.medicalBackground.medicalPathology || MedicalPathology.HEART_DISEASE,
        chirurgicalPathology: this.medicalBackground.chirurgicalPathology || ChirurgicalPathology.APPENDECTOMY,
        comment: this.medicalBackground.comment || '',
        date: this.medicalBackground.date ? this.medicalBackground.date.toISOString().split('T')[0] : null
      });
    }
  }

  updateFormValidators(type: string): void {
    const controls = this.medicalBackgroundForm.controls;

    controls['familialPathology'].clearValidators();
    controls['allergies'].clearValidators();
    controls['medicalPathology'].clearValidators();
    controls['chirurgicalPathology'].clearValidators();
    controls['date'].clearValidators();
    controls['comment'].clearValidators();

    switch (type) {
      case 'Familial':
        controls['familialPathology'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        break;
      case 'Allergies':
        controls['allergies'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        controls['date'].setValidators(Validators.required);
        break;
      case 'Medical':
        controls['medicalPathology'].setValidators(Validators.required);
        controls['date'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        break;
      case 'Chirurgical':
        controls['chirurgicalPathology'].setValidators(Validators.required);
        controls['date'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        break;
    }

    Object.values(controls).forEach(control => control.updateValueAndValidity());
  }

  getFormData(): MedicalBackground | null {
    if (this.medicalBackgroundForm.valid) {
      const formValue = this.medicalBackgroundForm.value;
      return new MedicalBackground(
        0,
        formValue.familialPathology,
        formValue.allergies,
        formValue.medicalPathology,
        formValue.chirurgicalPathology,
        '',
        formValue.comment,
        formValue.date ? new Date(formValue.date) : new Date(),
        this.data.backgroundType,
        this.data.medicalRecordId
      );
    }
    return null;
  }

  onDelete(): void {
    this.formDeleted.emit();
  }
}
