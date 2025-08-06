import { Component, Inject, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
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
export class MedicalBackgroundListComponent   implements OnInit {
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

  constructor(private medicalBackgroundService: MedicalBackgroundService,@Inject(MAT_DIALOG_DATA) public data: { medicalBackgroundList: MedicalBackground[]; medicalRecordId?: number }) {
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

    const validRecords: MedicalBackground[] = [];
    this.formComponents.forEach(form => {
      const record = form.getFormData();
      if (record) {
        validRecords.push(record);
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

    if (validRecords.length > 0) {
      this.medicalBackgroundService.addMBList(validRecords).subscribe({
        next: (created) => {
          console.log('Medical Backgrounds created:', created);
          this.familialRecords = created.filter(r => r.backgroundType === 'Familial');
          this.allergiesRecords = created.filter(r => r.backgroundType === 'Allergies');
          this.medicalRecords = created.filter(r => r.backgroundType === 'Medical');
          this.chirurgicalRecords = created.filter(r => r.backgroundType === 'Chirurgical');
        },
        error: (error) => {
          console.error('Error creating Medical Backgrounds:', error);
          // Keep local records if backend fails
        }
      });
    }
  }
}