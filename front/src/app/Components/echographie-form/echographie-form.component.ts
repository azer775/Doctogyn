import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Echographie } from '../../Models/Echographie';
import { UterusSize, Myometre, Ovary, Diagnosticpresumption, Pelvicdiagnosticpresumption } from '../../Models/enums';
import { EchographieService } from '../../Services/echographie.service';
import { CommonModule } from '@angular/common';
import { EditorComponent } from '../editor/editor.component';

@Component({
  selector: 'app-echographie-form',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, EditorComponent],
  templateUrl: './echographie-form.component.html',
  styleUrl: './echographie-form.component.css'
})
export class EchographieFormComponent implements OnInit {
  echographieForm: FormGroup;
  uterusSizes = Object.values(UterusSize);
  myometres = Object.values(Myometre);
  ovaries = Object.values(Ovary);
  diagnosticPresumptions = Object.values(Diagnosticpresumption);
  pelvicDiagnosticPresumptions = Object.values(Pelvicdiagnosticpresumption);
  dropdownOpen: { [key: string]: boolean } = {
    diagnosticpresumptionsOR: false,
    diagnosticpresumptionsOL: false,
    pelvicdiagnosticpresumptionsR: false,
    pelvicdiagnosticpresumptionsL: false
  };
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

  constructor(
    private fb: FormBuilder,
    private echographieService: EchographieService
  ) {
    this.echographieForm = this.fb.group({
      date: ['', Validators.required],
      report: ['<p>Default content</p>', Validators.required],
      cycleDay: ['', Validators.required],
      condition: ['', Validators.required],
      uterusSize: ['', Validators.required],
      uterusLength: [null, [Validators.min(0)]],
      uterusWidth: [null, [Validators.min(0)]],
      myometre: ['', Validators.required],
      myomesNumber: [null, [Validators.min(0)]],
      endometriumThickness: [null, [Validators.min(0)]],
      comment: [''],
      ovaryR: ['', Validators.required],
      ovaryRSize: [null, [Validators.min(0)]],
      cystSizeOR: [null, [Validators.min(0)]],
      diagnosticpresumptionsOR: [[]],
      ovaryRComment: [''],
      ovaryL: ['', Validators.required],
      ovaryLSize: [null, [Validators.min(0)]],
      cystSizeOL: [null, [Validators.min(0)]],
      diagnosticpresumptionsOL: [[]],
      ovaryLComment: [''],
      pelvicMR: [false],
      pmRSize: [null, [Validators.min(0)]],
      pelvicdiagnosticpresumptionsR: [[]],
      pmRComment: [''],
      pelvicML: [false],
      pmLSize: [null, [Validators.min(0)]],
      pelvicdiagnosticpresumptionsL: [[]],
      pmLComment: [''],
      consultationId: [null]
    });
  }

  ngOnInit() {}

  toggleDropdown(field: string) {
    this.dropdownOpen[field] = !this.dropdownOpen[field];
  }

  updateMultiSelect(field: string, value: string, event: Event) {
    const input = event.target as HTMLInputElement;
    const currentValues = this.echographieForm.get(field)?.value || [];
    if (input.checked) {
      this.echographieForm.get(field)?.setValue([...currentValues, value]);
    } else {
      this.echographieForm.get(field)?.setValue(currentValues.filter((v: string) => v !== value));
    }
  }

  onSubmit() {
    if (this.echographieForm.valid) {
      const echographie: Echographie = {
        ...this.echographieForm.value,
        date: new Date(this.echographieForm.value.date),
        diagnosticpresumptionsOR: this.echographieForm.value.diagnosticpresumptionsOR || [],
        diagnosticpresumptionsOL: this.echographieForm.value.diagnosticpresumptionsOL || [],
        pelvicdiagnosticpresumptionsR: this.echographieForm.value.pelvicdiagnosticpresumptionsR || [],
        pelvicdiagnosticpresumptionsL: this.echographieForm.value.pelvicdiagnosticpresumptionsL || []
      };
      console.log('Echographie Data:', echographie);
      this.echographieService.createEchographie(echographie).subscribe({
        next: (response) => {
          console.log('Echographie created successfully:', response);
        },
        error: (error) => {
          console.error('Error creating echographie:', error);
        }
      });
    } else {
      console.log('Form is invalid');
      this.echographieForm.markAllAsTouched();
    }
  }
}