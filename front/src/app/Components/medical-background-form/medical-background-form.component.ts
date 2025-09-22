import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators ,ReactiveFormsModule} from '@angular/forms';
import { FamilialPathology, Allergies, MedicalPathology, ChirurgicalPathology, BackgroundType } from '../../Models/enums';
import { MedicalBackground } from '../../Models/MedicalBackground';
import { MedicalBackgroundService } from '../../Services/medical-background.service';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule, MatDatepicker } from '@angular/material/datepicker';
import { MatNativeDateModule, MatOption } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
@Component({
  selector: 'app-medical-background-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, MatFormFieldModule, MatSelectModule, MatInputModule, MatButtonModule],
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
      familialPathology: [null],
      allergies: [null],
      medicalPathology: [null],
      chirurgicalPathology: [null],
      comment: [''],
      date: [null]
    });
  }

  ngOnInit(): void {
    this.updateFormValidators(this.data.backgroundType);
    if (this.medicalBackground) {
      this.medicalBackgroundForm.patchValue({
        familialPathology: this.medicalBackground.familialPathology || FamilialPathology.OVARIAN_CANCER,
        allergies: this.medicalBackground.allergies || Allergies.Drug,
        medicalPathology: this.medicalBackground.medicalPathology || MedicalPathology.HeartDisease,
        chirurgicalPathology: this.medicalBackground.chirurgicalPathology || ChirurgicalPathology.APPENDECTOMY,
        comment: this.medicalBackground.comment || '',
        date: this.medicalBackground.date ? this.medicalBackground.date : null
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
        break;
      case 'Allergies':
        controls['allergies'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        controls['date'].setValidators(Validators.required);
        break;
      case 'Medical':
        controls['medicalPathology'].setValidators(Validators.required);
//        controls['date'].setValidators(Validators.required);
        controls['comment'].setValidators(Validators.required);
        break;
      case 'Chirurgical':
        controls['chirurgicalPathology'].setValidators(Validators.required);
        break;
    }

    Object.values(controls).forEach(control => control.updateValueAndValidity());
  }

  getFormData(): MedicalBackground | null {
    if (this.medicalBackgroundForm.valid) {
      const formValue = this.medicalBackgroundForm.value;
      if(this.medicalBackground)
        {return new MedicalBackground(
          this.medicalBackground.id,
          formValue.familialPathology,
          formValue.allergies,
          formValue.medicalPathology,
          formValue.chirurgicalPathology,
          '',
          formValue.comment,
          formValue.date ? new Date(formValue.date) : new Date(),
          this.data.backgroundType,
          this.data.medicalRecordId
        );}
        else
        {
          return new MedicalBackground(
            null,
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
    }
    return null;
  }

  onDelete(): void {
    this.formDeleted.emit();
  }
}
