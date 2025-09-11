import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Consultation } from '../../Models/Consultation';
import { ConsultationType, Status } from '../../Models/enums';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';
import { NgxSummernoteModule } from 'ngx-summernote';
import { EditorComponent } from '../editor/editor.component';
import { Echographie } from '../../Models/Echographie';
import { EchographieFormComponent } from '../echographie-form/echographie-form.component';
import { AnalysesListComponent } from '../analyses-list/analyses-list.component';
import { MatDialog } from '@angular/material/dialog';
import { EmailsComponent } from '../emails/emails.component';
import { ExtractionResponse } from '../../Models/ExtractionResponse';
import { Document } from '../../Models/Document';
import { Bacteriology } from '../../Models/Bacteriology';
import { Biology } from '../../Models/Biology';
import { BloodGroup } from '../../Models/BloodGroup';
import { Radiology } from '../../Models/Radiology';
import { Serology } from '../../Models/Serology';
import { SpermAnalysis } from '../../Models/SpermAnalysis';

@Component({
  selector: 'app-consultation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxSummernoteModule, EditorComponent, EchographieFormComponent, AnalysesListComponent],
  templateUrl: './consultation-form.component.html',
  styleUrl: './consultation-form.component.css'
})
export class ConsultationFormComponent  implements OnInit {
  @Input() consultationId: number | null = null;
  @Output() formSubmitted = new EventEmitter<void>(); // New output
  consultationForm: FormGroup;
  consultationTypes = Object.values(ConsultationType);
  Status = Object.values(Status);
  content: string = '<p>Default content</p>';
  showEchographyForm: boolean = false;
  isEchographyCollapsed: boolean = false;
  echographies: Echographie[] = [];
  selectedEchography: Echographie | null = null;
  
  // New properties for analyses dropdown and collected data
  showAnalysesDropdown: boolean = false;
  collectedAnalysesData: ExtractionResponse | null = null;

  constructor(
    private fb: FormBuilder,
    private consultationService: ConsultationService,
    private dialog: MatDialog
  ) {
    this.consultationForm = this.fb.group({
      date: ['', Validators.required],
      signsNegates: ['', Validators.required],
      weight: [null, [Validators.min(0)]],
      length: [null, [Validators.required, Validators.min(0)]],
      bmi: [null, [Validators.required, Validators.min(0)]],
      breasts: [Status.Normal, Validators.required],
      examination: [''],
      vagina: [Status.Normal, Validators.required],
      consultationType: ['', Validators.required],
      gynecologySubRecordId: [null],
      fertilitySubRecordId: [null],
      obstetricsRecordId: [null]
    });
  }

  defaultConfig: any = {
    height: 300,
    focus: true,
    tableClassName: 'custom-table',
    toolbar: [
      ['style', ['bold', 'italic', 'underline']],
      ['para', ['ul', 'ol']],
      ['table', ['table']],
      ['insert', ['link', 'picture']]
    ],
    popover: {
      table: [
        ['add', ['addRowDown', 'addRowUp', 'addColLeft', 'addColRight']],
        ['delete', ['deleteRow', 'deleteCol', 'deleteTable']],
        ['custom', ['tableStyles']]
      ]
    },
    tableStyles: {
      'table-striped': 'Striped',
      'table-bordered': 'Bordered',
      'custom-table': 'Custom Style'
    }
  };

  ngOnInit() {
    if (this.consultationId) {
      this.consultationService.getConsultation(this.consultationId).subscribe({
        next: (consultation) => {
          this.consultationForm.patchValue({
            date: consultation.date ? new Date(consultation.date).toISOString().split('T')[0] : '',
            signsNegates: consultation.signsNegates,
            weight: consultation.weight,
            length: consultation.length,
            bmi: consultation.bmi,
            breasts: consultation.breasts,
            examination: consultation.examination,
            vagina: consultation.vagina,
            consultationType: consultation.consultationType,
            gynecologySubRecordId: consultation.gynecologySubRecordId,
            fertilitySubRecordId: consultation.fertilitySubRecordId,
            obstetricsRecordId: consultation.obstetricsRecordId
          });
          this.echographies = consultation.echographies || [];
          
          // Load analyses data if available
          if (consultation.extractionAnalyses) {
            this.collectedAnalysesData = consultation.extractionAnalyses;
            console.log('Loaded analyses data for editing:', this.collectedAnalysesData);
          }
          
          console.log('Fetched consultation:', consultation);
        },
        error: (error) => {
          console.error('Error fetching consultation:', error);
        }
      });
    }
  }

  toggleEchographyForm() {
    this.showEchographyForm = !this.showEchographyForm;
    this.selectedEchography = null;
  }

  toggleEchographyCollapse() {
    this.isEchographyCollapsed = !this.isEchographyCollapsed;
    if (this.isEchographyCollapsed) {
      this.showEchographyForm = false;
      this.selectedEchography = null;
    }
  }

  closeEchographyForm() {
    this.showEchographyForm = false;
    this.selectedEchography = null;
  }

  editEchography(index: number) {
    this.selectedEchography = this.echographies[index];
    this.showEchographyForm = true;
    this.isEchographyCollapsed = false;
  }

  addEchography(echography: Echographie) {
    if (this.selectedEchography && echography.id === this.selectedEchography.id) {
      const index = this.echographies.findIndex(e => e.id === echography.id);
      this.echographies[index] = echography;
    } else {
      this.echographies.push(echography);
    }
    this.showEchographyForm = false;
    this.selectedEchography = null;
  }

  removeEchography(index: number) {
    this.echographies.splice(index, 1);
  }

  onSubmit() {
    if (this.consultationForm.valid) {
      const consultation: Consultation = {
        ...this.consultationForm.value,
        date: new Date(this.consultationForm.value.date),
        examination: this.consultationForm.get('examination')?.value,
        echographies: this.echographies,
        extractionAnalyses: this.collectedAnalysesData || null
      };

      console.log('Consultation object being submitted:', consultation);
      console.log('Analyses data being included:', this.collectedAnalysesData);

      if (this.consultationId) {
        this.consultationService.updateConsultation(this.consultationId, consultation).subscribe({
          next: (response) => {
            console.log('Consultation updated successfully:', response);
            this.consultationForm.reset();
            this.echographies = [];
            this.collectedAnalysesData = null; // Reset analyses data
            this.consultationId = null;
            this.formSubmitted.emit(); // Emit event to reset edit state
          },
          error: (error) => {
            console.log('Error updating consultation:', consultation);
            console.error('Error updating consultation:', error);
          }
        });
      } else {
        this.consultationService.createConsultation(consultation).subscribe({
          next: (response) => {
            console.log('Consultation created successfully:', response);
            this.consultationForm.reset();
            this.echographies = [];
            this.collectedAnalysesData = null; // Reset analyses data
            this.formSubmitted.emit(); // Emit event to reset edit state
          },
          error: (error) => {
            console.error('Error creating consultation:', error);
          }
        });
      }
    } else {
      console.log('Form is invalid');
      this.consultationForm.markAllAsTouched();
    }
  }

  // ===== ANALYSES METHODS =====

  /**
   * Toggles the analyses dropdown menu
   */
  toggleAnalysesDropdown(): void {
    this.showAnalysesDropdown = !this.showAnalysesDropdown;
  }

  /**
   * Opens the emails dialog for medical analyses processing
   */
  openEmailsDialog(): void {
    this.showAnalysesDropdown = false; // Close dropdown
    
    const dialogRef = this.dialog.open(EmailsComponent, {
      width: '98vw',
      height: '95vh',
      maxWidth: '100vw',
      maxHeight: '100vh',
      panelClass: ['emails-dialog', 'fullscreen-dialog'],
      disableClose: false,
      autoFocus: false,
      restoreFocus: false,
      hasBackdrop: true,
      backdropClass: 'emails-dialog-backdrop',
      position: {
        top: '2.5vh',
        left: '1vw'
      },
      data: { source: 'consultation-form' }
    });

    // Handle dialog events
    dialogRef.afterOpened().subscribe(() => {
      console.log('Emails dialog opened from consultation form');
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Emails dialog closed', result);
      
      // Handle collected analyses data
      if (result && result.type === 'analysesCollected') {
        this.handleCollectedAnalyses(result.data);
      }
    });
  }

  /**
   * Handles collected analyses data from the emails dialog
   * Transforms the data into ExtractionResponse format for AnalysesListComponent
   * @param collectedData - Raw collected analyses data
   */
  handleCollectedAnalyses(collectedData: {
    biologies: Biology[],
    bacteriologies: Bacteriology[],
    bloodGroups: BloodGroup[],
    radiologies: Radiology[],
    serologies: Serology[],
    spermAnalyses: SpermAnalysis[]
  }): void {
    console.log('Processing collected analyses data in consultation form:', collectedData);
    
    // Create a Document object with all the collected data
    const document = new Document();
    document.biologies = collectedData.biologies || [];
    document.bacteriologies = collectedData.bacteriologies || [];
    document.bloodgroups = collectedData.bloodGroups || []; // Note: using 'bloodgroups' to match Document model
    document.radiologies = collectedData.radiologies || [];
    document.serologies = collectedData.serologies || [];
    document.spermAnalyses = collectedData.spermAnalyses || [];

    // Create ExtractionResponse with the document
    const extractionResponse = new ExtractionResponse();
    extractionResponse.documents = [document];

    // Set the data for the analyses list component
    this.collectedAnalysesData = extractionResponse;
    
    console.log('Created ExtractionResponse for consultation AnalysesListComponent:', this.collectedAnalysesData);
    
    // Optional: Show success message
    const totalAnalyses = this.getTotalAnalysesCount(collectedData);
    alert(`Successfully collected ${totalAnalyses} analyses and loaded into consultation form.`);
  }

  /**
   * Counts total number of analyses across all types
   */
  private getTotalAnalysesCount(data: any): number {
    return (data.biologies?.length || 0) +
           (data.bacteriologies?.length || 0) +
           (data.bloodGroups?.length || 0) +
           (data.radiologies?.length || 0) +
           (data.serologies?.length || 0) +
           (data.spermAnalyses?.length || 0);
  }

  /**
   * Gets total count of analyses from ExtractionResponse format
   */
  getTotalAnalysesCountFromExtraction(): number {
    if (!this.collectedAnalysesData || !this.collectedAnalysesData.documents) {
      return 0;
    }
    
    return this.collectedAnalysesData.documents.reduce((total, document) => {
      return total +
        (document.biologies?.length || 0) +
        (document.bacteriologies?.length || 0) +
        (document.bloodgroups?.length || 0) +
        (document.radiologies?.length || 0) +
        (document.serologies?.length || 0) +
        (document.spermAnalyses?.length || 0);
    }, 0);
  }
}