import { Component, Input, OnInit } from '@angular/core';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { CommonModule } from '@angular/common';
import { MedicalBackgroundFormComponent } from "../medical-background-form/medical-background-form.component";
import { MedicalBackgroundListComponent } from '../medical-background-list/medical-background-list.component';
import { MatDialog } from '@angular/material/dialog';
import { MedicalBackgroundTableComponent } from "../medical-background-table/medical-background-table.component";
import { MedicalRecordService } from '../../Services/medical-record.service';
import { SummaryComponent } from "../summary/summary.component";
import { ReportRequest } from '../../Models/ReportRequest';
import { AnalyseReport } from '../../Models/AnalyseReport';
import { FertilitySubRecord } from '../../Models/FertilitySubRecord';
import { GynecologySubRecord } from '../../Models/GynecologySubRecord';
import { ObstetricsRecord } from '../../Models/ObstetricsRecord';
import { ReportviewerComponent } from '../reportviewer/reportviewer.component';

@Component({
  selector: 'app-medical-record-preview',
  standalone: true,
  imports: [CommonModule, MedicalBackgroundTableComponent],
  templateUrl: './medical-record-preview.component.html',
  styleUrl: './medical-record-preview.component.css'
})
export class MedicalRecordPreviewComponent implements OnInit {
  @Input() medicalRecord: MedicalRecord | null = null;

  // Subrecords selection
  selectedSubrecords: {
    fertility: FertilitySubRecord[];
    gynecology: GynecologySubRecord[];
    obstetrics: ObstetricsRecord[];
  } = {
    fertility: [],
    gynecology: [],
    obstetrics: []
  };

  // Boolean attributes selection
  booleanAttributes = [
    { key: 'familialBackground', label: 'Familial Background', value: false, target: 'reportRequest' },
    { key: 'medicalBackground', label: 'Medical Background', value: false, target: 'reportRequest' },
    { key: 'chirurgicalBackground', label: 'Chirurgical Background', value: false, target: 'reportRequest' },
    { key: 'allergiesBackground', label: 'Allergies Background', value: false, target: 'reportRequest' },
    { key: 'echography', label: 'Echography', value: false, target: 'reportRequest' },
    { key: 'bacteriology', label: 'Bacteriology', value: false, target: 'analyseReport' },
    { key: 'serology', label: 'Serology', value: false, target: 'analyseReport' },
    { key: 'bloodGroup', label: 'Blood Group', value: false, target: 'analyseReport' },
    { key: 'spermAnalysis', label: 'Sperm Analysis', value: false, target: 'analyseReport' },
    { key: 'biology', label: 'Biology', value: false, target: 'analyseReport' },
    { key: 'radiology', label: 'Radiology', value: false, target: 'analyseReport' }
  ];

  reportRequest: ReportRequest = new ReportRequest();

  // Dropdown states
  isSubrecordsDropdownOpen = false;
  isBooleanAttributesDropdownOpen = false;

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

  // Toggle subrecord selection
  toggleSubrecordSelection(type: 'fertility' | 'gynecology' | 'obstetrics', record: any): void {
    const selectedArray = this.selectedSubrecords[type];
    const index = selectedArray.findIndex((r: any) => r.id === record.id);
    
    if (index > -1) {
      selectedArray.splice(index, 1);
    } else {
      selectedArray.push(record);
    }
  }

  // Check if subrecord is selected
  isSubrecordSelected(type: 'fertility' | 'gynecology' | 'obstetrics', record: any): boolean {
    return this.selectedSubrecords[type].some((r: any) => r.id === record.id);
  }

  // Select all subrecords
  selectAllSubrecords(): void {
    if (this.medicalRecord) {
      this.selectedSubrecords.fertility = [...(this.medicalRecord.fertilitySubRecords || [])];
      this.selectedSubrecords.gynecology = [...(this.medicalRecord.gynecologySubRecords || [])];
      this.selectedSubrecords.obstetrics = [...(this.medicalRecord.obstetricsRecords || [])];
    }
  }

  // Deselect all subrecords
  deselectAllSubrecords(): void {
    this.selectedSubrecords.fertility = [];
    this.selectedSubrecords.gynecology = [];
    this.selectedSubrecords.obstetrics = [];
  }

  // Toggle boolean attribute
  toggleBooleanAttribute(attribute: any): void {
    attribute.value = !attribute.value;
  }

  // Toggle dropdown states
  toggleSubrecordsDropdown(): void {
    this.isSubrecordsDropdownOpen = !this.isSubrecordsDropdownOpen;
  }

  toggleBooleanAttributesDropdown(): void {
    this.isBooleanAttributesDropdownOpen = !this.isBooleanAttributesDropdownOpen;
  }

  // Select all boolean attributes
  selectAllBooleanAttributes(): void {
    this.booleanAttributes.forEach(attr => attr.value = true);
  }

  // Deselect all boolean attributes
  deselectAllBooleanAttributes(): void {
    this.booleanAttributes.forEach(attr => attr.value = false);
  }

  // Generate report request
  generateReportRequest(): void {
    if (!this.medicalRecord?.id) {
      console.error('No medical record ID available');
      return;
    }

    // Initialize the report request
    this.reportRequest = new ReportRequest();
    this.reportRequest.analyseReport = new AnalyseReport();

    // Assign selected subrecords
    this.reportRequest.fertilitySubRecords = [...this.selectedSubrecords.fertility];
    this.reportRequest.gynecologySubRecords = [...this.selectedSubrecords.gynecology];
    this.reportRequest.obstetricsRecords = [...this.selectedSubrecords.obstetrics];

    // Assign boolean attributes
    this.booleanAttributes.forEach(attr => {
      if (attr.target === 'reportRequest') {
        (this.reportRequest as any)[attr.key] = attr.value;
      } else if (attr.target === 'analyseReport') {
        (this.reportRequest.analyseReport as any)[attr.key] = attr.value;
      }
    });

    // Call the service to get the report
    this.medicalRecordService.getReport(this.medicalRecord.id, this.reportRequest).subscribe({
      next: (report) => {
        console.log('Report generated successfully:', report);
        // Open the report viewer dialog
        const dialogRef = this.dialog.open(ReportviewerComponent, {
          data: { report: report },
          width: '90vw',
          maxHeight: '90vh',
          autoFocus: true,
          panelClass: 'custom-dialog-container'
        });

        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            console.log('Report saved:', result);
            // You can add additional logic here, such as saving the edited report
          }
        });
      },
      error: (error) => {
        console.error('Error generating report:', error);
        // You can add additional error handling logic here
      }
    });
  }
}