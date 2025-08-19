import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Consultation } from '../../Models/Consultation';
import { ConsultationType, Status } from '../../Models/enums';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';
import { NgxSummernoteModule } from 'ngx-summernote';
import { EditorComponent } from '../editor/editor.component';
import { Echographie } from '../../Models/Echographie';
import { EchographieFormComponent } from '../echographie-form/echographie-form.component';

@Component({
  selector: 'app-consultation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxSummernoteModule, EditorComponent,EchographieFormComponent],
  templateUrl: './consultation-form.component.html',
  styleUrl: './consultation-form.component.css'
})
export class ConsultationFormComponent  implements OnInit {
  @Input() consultationId: number | null = null;
  consultationForm: FormGroup;
  consultationTypes = Object.values(ConsultationType);
  Status = Object.values(Status);
  content: string = '<p>Default content</p>';
  showEchographyForm: boolean = false;
  isEchographyCollapsed: boolean = false;
  echographies: Echographie[] = [];
  selectedEchography: Echographie | null = null;

  constructor(
    private fb: FormBuilder,
    private consultationService: ConsultationService
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
        echographies: this.echographies
      };

      if (this.consultationId) {
        this.consultationService.updateConsultation(this.consultationId, consultation).subscribe({
          next: (response) => {
            console.log('Consultation updated successfully:', response);
            this.consultationForm.reset();
            this.echographies = [];
            this.consultationId = null;
          },
          error: (error) => {
            console.error('Error updating consultation:', error);
          }
        });
      } else {
        this.consultationService.createConsultation(consultation).subscribe({
          next: (response) => {
            console.log('Consultation created successfully:', response);
            this.consultationForm.reset();
            this.echographies = [];
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
}