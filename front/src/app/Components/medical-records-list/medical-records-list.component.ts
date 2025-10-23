import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { TokenService } from '../../Services/token.service';
import { MedicalRecordFormComponent } from '../medical-record-form/medical-record-form.component';

@Component({
  selector: 'app-medical-records-list',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './medical-records-list.component.html',
  styleUrl: './medical-records-list.component.css'
})
export class MedicalRecordsListComponent implements OnInit {
  medicalRecords: MedicalRecord[] = [];
  isLoading = true;

  constructor(
    private medicalRecordService: MedicalRecordService,
    private router: Router,
    private token: TokenService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadMedicalRecords();
    console.log(this.token.userRoles);
  }

  loadMedicalRecords(): void {
    this.isLoading = true;
    this.medicalRecordService.getCabinetMedicalRecords().subscribe({
      next: (records) => {
        this.medicalRecords = records;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading medical records:', error);
        this.isLoading = false;
      }
    });
  }

  openAddMedicalRecordDialog(): void {
    const dialogRef = this.dialog.open(MedicalRecordFormComponent, {
      width: '800px',
      maxHeight: '90vh',
      panelClass: 'custom-dialog-container',
      disableClose: false
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'success') {
        this.loadMedicalRecords();
      }
    });
  }

  showMedicalRecord(id: number): void {
    this.router.navigate(['/dashboard/medical-record', id]);
  }

  deleteMedicalRecord(id: number): void {
    if (confirm('Are you sure you want to delete this medical record?')) {
      this.medicalRecordService.deleteMedicalRecord(id).subscribe({
        next: () => {
          this.loadMedicalRecords();
        },
        error: (error) => {
          console.error('Error deleting medical record:', error);
        }
      });
    }
  }
}
