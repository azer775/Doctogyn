import { Component, Inject, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MedicalBackground } from '../../Models/MedicalBackground';
import { MedicalBackgroundService } from '../../Services/medical-background.service';
import { MedicalBackgroundFormComponent } from '../medical-background-form/medical-background-form.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-medical-background-list',
  standalone: true,
  imports: [CommonModule, MedicalBackgroundFormComponent],
  templateUrl: './medical-background-list.component.html',
  styleUrl: './medical-background-list.component.css',
  providers: [MedicalBackgroundService]
})
export class MedicalBackgroundListComponent implements OnInit {
  medicalRecordId: number = 1;
  familialRecords: MedicalBackground[] = [];
  allergiesRecords: MedicalBackground[] = [];
  medicalRecords: MedicalBackground[] = [];
  chirurgicalRecords: MedicalBackground[] = [];
  formInstances: { [key: string]: { index: number; data: MedicalBackground | null }[] } = {
    Familial: [],
    Allergies: [],
    Medical: [],
    Chirurgical: []
  };
  backgroundTypes: string[] = ['Familial', 'Allergies', 'Medical', 'Chirurgical'];
  @ViewChildren(MedicalBackgroundFormComponent) formComponents!: QueryList<MedicalBackgroundFormComponent>;
  medicalBackgroundList: MedicalBackground[] = [];

  constructor(
    private medicalBackgroundService: MedicalBackgroundService,
    @Inject(MAT_DIALOG_DATA) public data: { medicalBackgroundList: MedicalBackground[]; medicalRecordId?: number },
    private dialogRef: MatDialogRef<MedicalBackgroundListComponent>
  ) {
    this.medicalBackgroundList = data?.medicalBackgroundList || [];
    this.medicalRecordId = data?.medicalRecordId || 1;
  }

  ngOnInit(): void {
    this.initializeForms();
  }

  initializeForms(): void {
    this.formInstances = {
      Familial: [],
      Allergies: [],
      Medical: [],
      Chirurgical: []
    };
    this.medicalBackgroundList.forEach((record, index) => {
      this.formInstances[record.backgroundType].push({ index, data: record });
    });
  }

  toggleForm(backgroundType: string): void {
    this.formInstances[backgroundType].push({ index: this.formInstances[backgroundType].length, data: null });
  }

  removeForm(backgroundType: string, index: number): void {
    this.formInstances[backgroundType] = this.formInstances[backgroundType].filter(form => form.index !== index);
  }

  addList(): void {
    this.familialRecords = [];
    this.allergiesRecords = [];
    this.medicalRecords = [];
    this.chirurgicalRecords = [];

    const records: MedicalBackground[] = [];
    this.formComponents.forEach(form => {
      const record = form.getFormData();
      if (record) {
        // Preserve ID for existing records; new records keep id as undefined
        records.push(record);
        switch (record.backgroundType) {
          case 'Familial':
            this.familialRecords.push(record);
            break;
          case 'Allergies':
            this.allergiesRecords.push(record);
            break;
          case 'Medical':
            this.medicalRecords.push(record);
            break;
          case 'Chirurgical':
            this.chirurgicalRecords.push(record);
            break;
        }
      }
    });
    console.log('Records to be added:', records); // Debug log
    if (records.length > 0) {
      // Use updateOrAddMBList to handle both updates (with IDs) and additions (without IDs)
      this.medicalBackgroundService.addMBList(records).subscribe({
        next: (updatedRecords) => {
          console.log('Medical Backgrounds processed:', updatedRecords);
          this.familialRecords = updatedRecords.filter(r => r.backgroundType === 'Familial');
          this.allergiesRecords = updatedRecords.filter(r => r.backgroundType === 'Allergies');
          this.medicalRecords = updatedRecords.filter(r => r.backgroundType === 'Medical');
          this.chirurgicalRecords = updatedRecords.filter(r => r.backgroundType === 'Chirurgical');
          this.dialogRef.close({ success: true });
        },
        error: (error) => {
          console.error('Error processing Medical Backgrounds:', error);
          // Keep local records if backend fails
        }
      });
    }
  }
}