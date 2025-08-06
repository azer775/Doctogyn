import { Component, Input } from '@angular/core';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { CommonModule } from '@angular/common';
import { MedicalBackgroundFormComponent } from "../medical-background-form/medical-background-form.component";
import { MedicalBackgroundListComponent } from '../medical-background-list/medical-background-list.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-medical-record-preview',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './medical-record-preview.component.html',
  styleUrl: './medical-record-preview.component.css'
})
export class MedicalRecordPreviewComponent {
  @Input() medicalRecord: MedicalRecord | null = null;
  constructor(private dialog: MatDialog) {}

    openMedicalBackgroundDialog(): void {
    if (this.medicalRecord?.medicalBackgrounds) {
      console.log('Opening dialog with medicalBackgrounds:', this.medicalRecord.medicalBackgrounds); // Debug log
      this.dialog.open(MedicalBackgroundListComponent, {
        data: { medicalBackgroundList: this.medicalRecord.medicalBackgrounds },
        width: '80%',
        maxWidth: '800px',
        maxHeight: '80vh',
        autoFocus: true,
        panelClass: 'custom-dialog-container', // Optional: for custom styling
      });
    } else {
      console.warn('No medical backgrounds available to display in dialog');
    }
  }
  

}
