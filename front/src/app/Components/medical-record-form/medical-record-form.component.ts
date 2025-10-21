import { Component } from '@angular/core';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { CivilState, Sex } from '../../Models/enums';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-medical-record-form',
  standalone: true,
  imports: [CommonModule, FormsModule, MatDialogModule],
  templateUrl: './medical-record-form.component.html',
  styleUrl: './medical-record-form.component.css'
})
export class MedicalRecordFormComponent {
   Sex = Object.values(Sex);
   CivilState = Object.values(CivilState);
   medicalRecord: MedicalRecord = {
    id: 0,
    name: '',
    surname: '',
    birthDate: new Date(),
    sex: Sex.Male,
    civilState: CivilState.Married,
    email: '',
    comment: '',
    fertilitySubRecords: [],
    obstetricsRecords: [],
    gynecologySubRecords: [],
    medicalBackgrounds: []
  };
  successMessage: string = '';
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private medicalRecordService: MedicalRecordService,
    public dialogRef: MatDialogRef<MedicalRecordFormComponent>
  ) {}
  
  addMedicalRecord(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isSubmitting = true;
    
    this.medicalRecordService.createMedicalRecord(this.medicalRecord).subscribe({
      next: (response) => {
        this.successMessage = 'Medical record added successfully!';
        setTimeout(() => {
          this.dialogRef.close('success');
        }, 1000);
      },
      error: (error) => {
        this.errorMessage = 'Failed to add medical record. Please try again.';
        console.error(error);
        this.isSubmitting = false;
      }
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  private resetForm(): void {
    this.medicalRecord = {
    id: 0,
    name: '',
    surname: '',
    birthDate: new Date(),
    sex: Sex.Male,
    civilState: CivilState.Married,
    email: '',
    comment: '',
    fertilitySubRecords: [],
    obstetricsRecords: [],
    gynecologySubRecords: [],
    medicalBackgrounds: []
  };
  }

}
