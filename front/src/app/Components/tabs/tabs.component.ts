import { CommonModule } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { MedicalRecordPreviewComponent } from '../medical-record-preview/medical-record-preview.component';
import { FertilitySubRecordDetailComponent } from '../fertility-sub-record-detail/fertility-sub-record-detail.component';
import { ObstetricsRecordDetailComponent } from '../obstetrics-record-detail/obstetrics-record-detail.component';
import { GynecologySubRecordDetailComponent } from '../gynecology-sub-record-detail/gynecology-sub-record-detail.component';

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports: [CommonModule, MedicalRecordPreviewComponent,FertilitySubRecordDetailComponent,ObstetricsRecordDetailComponent,GynecologySubRecordDetailComponent],
  templateUrl: './tabs.component.html',
  styleUrl: './tabs.component.css'
})
export class TabsComponent implements OnInit {
  tabs: { title: string; isPreview?: boolean; subRecordId?: number; type?: 'gynecology' | 'fertility' | 'obstetrics' }[] = [];
  activeTab = 0;
  medicalRecordId = 1; // Replace with dynamic ID (e.g., from route params)
  medicalRecord: MedicalRecord | null = null;

  constructor(private medicalRecordService: MedicalRecordService) {}

  ngOnInit(): void {
    this.fetchMedicalRecord();
  }

  fetchMedicalRecord(): void {
    this.medicalRecordService.getMedicalRecord(this.medicalRecordId).subscribe({
      next: (record: MedicalRecord) => {
        this.medicalRecord = record;
        this.tabs = [
          {
            title: 'Preview',
            isPreview: true,
          },
        ];
        // Add Gynecology SubRecords
        if (record.gynecologySubRecords) {
          record.gynecologySubRecords.forEach((sub) => {
            if (sub.date) {
              const date = new Date(sub.date);
              this.tabs.push({
                title: `Gynecology ${date.getMonth() + 1}/${date.getFullYear()}`,
                subRecordId: sub.id,
                type: 'gynecology',
              });
            }
          });
        }
        // Add Fertility SubRecords
        if (record.fertilitySubRecords) {
          record.fertilitySubRecords.forEach((sub) => {
            if (sub.infertility) {
              const date = new Date(sub.infertility);
              this.tabs.push({
                title: `Fertility ${date.getMonth() + 1}/${date.getFullYear()}`,
                subRecordId: sub.id,
                type: 'fertility',
              });
            }
          });
        }
        // Add Obstetrics SubRecords
        if (record.obstetricsRecords) {
          record.obstetricsRecords.forEach((sub) => {
            if (sub.conceptionDate) {
              const date = new Date(sub.conceptionDate);
              this.tabs.push({
                title: `Obstetrics ${date.getMonth() + 1}/${date.getFullYear()}`,
                subRecordId: sub.id,
                type: 'obstetrics',
              });
            }
          });
        }
      },
      error: (err) => {
        console.error('Error fetching medical record:', err);
        this.tabs = [
          {
            title: 'Error',
          },
        ];
      },
    });
  }

  setActiveTab(index: number) {
    this.activeTab = index;
  }

  @HostListener('keydown', ['$event'])
  handleKeydown(event: KeyboardEvent) {
    if (event.key === 'ArrowRight') {
      this.setActiveTab((this.activeTab + 1) % this.tabs.length);
    } else if (event.key === 'ArrowLeft') {
      this.setActiveTab((this.activeTab - 1 + this.tabs.length) % this.tabs.length);
    } else if (event.key === 'Enter' || event.key === ' ') {
      this.setActiveTab(this.activeTab);
    }
  }
}