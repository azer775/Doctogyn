import { Component, Input, OnInit } from '@angular/core';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { CommonModule } from '@angular/common';
import { MedicalBackgroundFormComponent } from "../medical-background-form/medical-background-form.component";
import { MedicalBackgroundListComponent } from '../medical-background-list/medical-background-list.component';
import { MatDialog } from '@angular/material/dialog';
import { MedicalBackgroundTableComponent } from "../medical-background-table/medical-background-table.component";
import { MedicalRecordService } from '../../Services/medical-record.service';
import { SummaryComponent } from "../summary/summary.component";

@Component({
  selector: 'app-medical-record-preview',
  standalone: true,
  imports: [CommonModule, MedicalBackgroundTableComponent],
  templateUrl: './medical-record-preview.component.html',
  styleUrl: './medical-record-preview.component.css'
})
export class MedicalRecordPreviewComponent implements OnInit {
  @Input() medicalRecord: MedicalRecord | null = null;

  constructor(
    private dialog: MatDialog,
    private medicalRecordService: MedicalRecordService
  ) {}

  ngOnInit(): void {
    this.loadMedicalRecord();
  }

  loadMedicalRecord(): void {
    if (this.medicalRecord?.id) {
      this.medicalRecordService.getMedicalRecord(this.medicalRecord.id).subscribe({
        next: (record) => {
          this.medicalRecord = record;
        },
        error: (error) => {
          console.error('Error loading medical record:', error);
        }
      });
    }
  }

  openSummaryDialog(): void {
    if (!this.medicalRecord?.id) {
      return;
    }
    this.dialog.open(SummaryComponent, {
      data: { medicalRecordId: this.medicalRecord.id },
      width: '80vw',
      maxHeight: '90vh',
      autoFocus: true,
      panelClass: 'custom-dialog-container'
    });
  }

  openMedicalBackgroundDialog(): void {
    if (this.medicalRecord?.medicalBackgrounds) {
      console.log('Opening dialog with medicalBackgrounds:', this.medicalRecord.medicalBackgrounds); // Debug log
      const dialogRef = this.dialog.open(MedicalBackgroundListComponent, {
        data: { medicalBackgroundList: this.medicalRecord.medicalBackgrounds, medicalRecordId: this.medicalRecord.id },
        width: '99%',
        maxHeight: '99vh',
        autoFocus: true,
        panelClass: 'custom-dialog-container'
      });

      dialogRef.afterClosed().subscribe(result => {
        if (result?.success) {
          console.log('Medical backgrounds updated, reloading medical record');
          this.loadMedicalRecord();
        }
      });
    } else {
      console.warn('No medical backgrounds available to display in dialog');
    }
  }
}