import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { MedicalRecord } from '../../Models/MedicalRecord';

@Component({
  selector: 'app-medical-records-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './medical-records-list.component.html',
  styleUrl: './medical-records-list.component.css'
})
export class MedicalRecordsListComponent implements OnInit {
  medicalRecords: MedicalRecord[] = [];
  isLoading = true;

  constructor(
    private medicalRecordService: MedicalRecordService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadMedicalRecords();
  }

  loadMedicalRecords(): void {
    this.isLoading = true;
    this.medicalRecordService.getAllMedicalRecords().subscribe({
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

  showMedicalRecord(id: number): void {
    this.router.navigate(['/medical-record', id]);
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
