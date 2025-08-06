import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Consultation } from '../../Models/Consultation';
import { ConsultationType, Status } from '../../Models/enums';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';
import { NgxSummernoteModule } from 'ngx-summernote';
import { EditorComponent } from '../editor/editor.component';

@Component({
  selector: 'app-consultation-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgxSummernoteModule, EditorComponent],
  templateUrl: './consultation-form.component.html',
  styleUrl: './consultation-form.component.css'
})
export class ConsultationFormComponent  implements OnInit {
  consultationForm: FormGroup;
  consultationTypes = Object.values(ConsultationType); 
  Status = Object.values(Status); 
  content: string = '<p>Default content</p>'; // For the editor content
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

  ngOnInit() {}

  onSubmit() {
    if (this.consultationForm.valid) {
      const consultation: Consultation = {
        ...this.consultationForm.value,
        date: new Date(this.consultationForm.value.date), // Convert string to Date
        examination: this.consultationForm.get('examination')?.value
      };
      console.log('Consultation Data:', consultation);
       this.consultationService.createConsultation(consultation).subscribe({
        next: (response) => {
          console.log('Consultation created successfully:', response);
          //this.consultationForm.reset();
        },
        error: (error) => {
          console.error('Error creating consultation:', error);
        }
      }); 
     } else {
      console.log('Form is invalid');
      this.consultationForm.markAllAsTouched();
    } 
  }

}
