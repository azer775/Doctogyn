import { Component } from '@angular/core';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { CivilState, Sex } from '../../Models/enums';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-medical-record-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  constructor(private medicalRecordService: MedicalRecordService) {}
    addMedicalRecord(): void {
    this.successMessage = '';
    this.errorMessage = '';
    
    this.medicalRecordService.createMedicalRecord(this.medicalRecord).subscribe({
      next: (response) => {
        this.successMessage = 'Medical record added successfully!';
        this.resetForm();
      },
      error: (error) => {
        this.errorMessage = 'Failed to add medical record. Please try again.';
        console.error(error);
      }
    });
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
