import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
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
  @Input() echographie: Echographie | null = null;
  @Input() consultationId: number | null = null; // New input for consultation ID
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
  @Output() echographySubmitted = new EventEmitter<Echographie>();
  @Output() closeForm = new EventEmitter<void>();

  defaultConfig: any = {
    width: 500,
    height: 75,
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
      pmLComment: ['']
    });
  }

  ngOnInit() {
    if (this.echographie) {
      this.echographieForm.patchValue({
        date: this.echographie.date ? new Date(this.echographie.date).toISOString().split('T')[0] : '',
        report: this.echographie.report,
        cycleDay: this.echographie.cycleDay,
        condition: this.echographie.condition,
        uterusSize: this.echographie.uterusSize,
        uterusLength: this.echographie.uterusLength,
        uterusWidth: this.echographie.uterusWidth,
        myometre: this.echographie.myometre,
        myomesNumber: this.echographie.myomesNumber,
        endometriumThickness: this.echographie.endometriumThickness,
        comment: this.echographie.comment,
        ovaryR: this.echographie.ovaryR,
        ovaryRSize: this.echographie.ovaryRSize,
        cystSizeOR: this.echographie.cystSizeOR,
        diagnosticpresumptionsOR: this.echographie.diagnosticpresumptionsOR || [],
        ovaryRComment: this.echographie.ovaryRComment,
        ovaryL: this.echographie.ovaryL,
        ovaryLSize: this.echographie.ovaryLSize,
        cystSizeOL: this.echographie.cystSizeOL,
        diagnosticpresumptionsOL: this.echographie.diagnosticpresumptionsOL || [],
        ovaryLComment: this.echographie.ovaryLComment,
        pelvicMR: this.echographie.pelvicMR,
        pmRSize: this.echographie.pmRSize,
        pelvicdiagnosticpresumptionsR: this.echographie.pelvicdiagnosticpresumptionsR || [],
        pmRComment: this.echographie.pmRComment,
        pelvicML: this.echographie.pelvicML,
        pmLSize: this.echographie.pmLSize,
        pelvicdiagnosticpresumptionsL: this.echographie.pelvicdiagnosticpresumptionsL || [],
        pmLComment: this.echographie.pmLComment
      });
    }
  }

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
      const echography: Echographie = {
        ...this.echographieForm.value,
        id: this.echographie?.id,
        date: new Date(this.echographieForm.value.date),
        diagnosticpresumptionsOR: this.echographieForm.value.diagnosticpresumptionsOR || [],
        diagnosticpresumptionsOL: this.echographieForm.value.diagnosticpresumptionsOL || [],
        pelvicdiagnosticpresumptionsR: this.echographieForm.value.pelvicdiagnosticpresumptionsR || [],
        pelvicdiagnosticpresumptionsL: this.echographieForm.value.pelvicdiagnosticpresumptionsL || []
      };

      if (this.echographie?.id) {
        echography.consultationId = this.echographie.consultationId; // Ensure consultationId is set
        // Update existing echography
        this.echographieService.updateEchographie(this.echographie.id, echography).subscribe({
          next: (response) => {
            console.log('Echography updated successfully:', response);
            this.echographySubmitted.emit(response);
            this.resetForm();
            this.echographie = null;
          },
          error: (error) => {
            console.error('Error updating echography:', error);
          }
        });
      } else {
        echography.consultationId = this.consultationId;
        // Create new echography
        this.echographySubmitted.emit(echography);
        this.resetForm();
      }
    } else {
      console.log('Form is invalid');
      this.echographieForm.markAllAsTouched();
    }
  }

  resetForm() {
    this.echographieForm.reset({
      date: '',
      report: '<p>Default content</p>',
      cycleDay: '',
      condition: '',
      uterusSize: '',
      uterusLength: null,
      uterusWidth: null,
      myometre: '',
      myomesNumber: null,
      endometriumThickness: null,
      comment: '',
      ovaryR: '',
      ovaryRSize: null,
      cystSizeOR: null,
      diagnosticpresumptionsOR: [],
      ovaryRComment: '',
      ovaryL: '',
      ovaryLSize: null,
      cystSizeOL: null,
      diagnosticpresumptionsOL: [],
      ovaryLComment: '',
      pelvicMR: false,
      pmRSize: null,
      pelvicdiagnosticpresumptionsR: [],
      pmRComment: '',
      pelvicML: false,
      pmLSize: null,
      pelvicdiagnosticpresumptionsL: [],
      pmLComment: '',
      consultationId: null
    });
  }

  closeEchographyForm() {
    this.resetForm();
    this.closeForm.emit();
  }
}