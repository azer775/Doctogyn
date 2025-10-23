import { CommonModule } from '@angular/common';
import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { MedicalRecordPreviewComponent } from '../medical-record-preview/medical-record-preview.component';
import { FertilitySubRecordDetailComponent } from '../fertility-sub-record-detail/fertility-sub-record-detail.component';
import { ObstetricsRecordDetailComponent } from '../obstetrics-record-detail/obstetrics-record-detail.component';
import { GynecologySubRecordDetailComponent } from '../gynecology-sub-record-detail/gynecology-sub-record-detail.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { FertilitySubRecordFormComponent } from '../fertility-sub-record-form/fertility-sub-record-form.component';
import { GynecologySubRecordFormComponent } from '../gynecology-sub-record-form/gynecology-sub-record-form.component';
import { ObstetricsRecordFormComponent } from '../obstetrics-record-form/obstetrics-record-form.component';

@Component({
  selector: 'app-tabs',
  standalone: true,
  imports: [CommonModule,MatDialogModule,MedicalRecordPreviewComponent,FertilitySubRecordDetailComponent,ObstetricsRecordDetailComponent,GynecologySubRecordDetailComponent,FertilitySubRecordFormComponent,GynecologySubRecordFormComponent,ObstetricsRecordFormComponent],
  templateUrl: './tabs.component.html',
  styleUrl: './tabs.component.css'
})
export class TabsComponent implements OnInit {
  tabs: { title: string; isPreview?: boolean; subRecordId?: number; type?: 'gynecology' | 'fertility' | 'obstetrics' }[] = [];
  activeTab = 0;
  medicalRecordId!: number; // Replace with dynamic ID (e.g., from route params)
  medicalRecord: MedicalRecord | null = null;
  isDropdownOpen = false;
  @ViewChild('addButton') addButton!: ElementRef;
  dropdownLeft = 0;
  dropdownTop = 0;

  constructor(
    private medicalRecordService: MedicalRecordService,
    private dialog: MatDialog,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Subscribe to route params
    this.route.params.subscribe(params => {
      // If an ID is provided in the route, use it; otherwise use the default (1)
      if (params['id']) {
        this.medicalRecordId = +params['id'];
      }
      this.fetchMedicalRecord();
    });
  }

  fetchMedicalRecord(): void {
    this.medicalRecordService.getMedicalRecord(this.medicalRecordId).subscribe({
      next: (record: MedicalRecord) => {
        this.medicalRecord = record;
        console.log('Fetched Medical Record:', record);
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
            const date = sub.date ? new Date(sub.date) : new Date();
            this.tabs.push({
              title: `Fertility ${date.getMonth() + 1}/${date.getFullYear()}`,
              subRecordId: sub.id,
              type: 'fertility',
            });
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
    this.closeDropdown();
  }

  toggleDropdown() {
  this.isDropdownOpen = !this.isDropdownOpen;
  if (this.isDropdownOpen) {
    const rect = this.addButton.nativeElement.getBoundingClientRect();
    this.dropdownLeft = rect.left;
    this.dropdownTop = rect.bottom;
  }
}

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  openFertilityForm() {
    this.closeDropdown();
    const dialogRef = this.dialog.open(FertilitySubRecordFormComponent, {
      data: { medicalRecordId: this.medicalRecordId },
      width: '80%',
      maxWidth: '800px',
      maxHeight: '80vh',
      autoFocus: true,
      panelClass: 'custom-dialog-container',
    });

    dialogRef.componentInstance.formSubmitted.subscribe(() => {
      dialogRef.close();
      this.fetchMedicalRecord(); // Refresh tabs after form submission
    });
  }

  openGynecologyForm() {
    this.closeDropdown();
    const dialogRef = this.dialog.open(GynecologySubRecordFormComponent, {
      data: { medicalRecordId: this.medicalRecordId },
      width: '80%',
      maxWidth: '800px',
      maxHeight: '80vh',
      autoFocus: true,
      panelClass: 'custom-dialog-container',
    });

    dialogRef.componentInstance.formSubmitted.subscribe(() => {
      dialogRef.close();
      this.fetchMedicalRecord(); // Refresh tabs after form submission
    });
  }

  openObstetricsForm() {
    console.log("id:", this.medicalRecordId);
    this.closeDropdown();
    const dialogRef = this.dialog.open(ObstetricsRecordFormComponent, {
      data: { medicalRecordId: this.medicalRecordId },
      width: '80%',
      maxWidth: '800px',
      maxHeight: '80vh',
      autoFocus: true,
      panelClass: 'custom-dialog-container',
    });

    dialogRef.componentInstance.formSubmitted.subscribe(() => {
      dialogRef.close();
      this.fetchMedicalRecord(); // Refresh tabs after form submission
    });
  }

  @HostListener('document:click', ['$event'])
  handleClickOutside(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.add-button') && !target.closest('.dropdown-menu')) {
      this.closeDropdown();
    }
  }

  
}