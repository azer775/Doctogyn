import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { ConceptionType } from '../../Models/enums';
import { ObstetricsRecord } from '../../Models/ObstetricsRecord';
import { ObstetricsRecordService } from '../../Services/obstetrics-record.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-obstetrics-record-form',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './obstetrics-record-form.component.html',
  styleUrl: './obstetrics-record-form.component.css'
})
export class ObstetricsRecordFormComponent implements OnInit {
  @Input() data: { medicalRecordId: number } = { medicalRecordId: 1 };
  obstetricsForm: FormGroup;
  ConceptionsTypes = Object.values(ConceptionType);
  constructor(
    private fb: FormBuilder,
    private obstetricsRecordService: ObstetricsRecordService
  ) {
    this.obstetricsForm = this.fb.group({
      conceptionType: [ConceptionType.Natural, Validators.required],
      conceptionDate: [null],
      ddr: [null],
      nfoetus: [1, [Validators.required, Validators.min(1)]],
      comment: ['']
    });
  }

  ngOnInit() {
    this.obstetricsForm.patchValue({
      medicalRecordId: this.data.medicalRecordId
    });
  }

  onSubmit() {
    if (this.obstetricsForm.valid) {
      const obstetricsRecord: ObstetricsRecord = {
        ...this.obstetricsForm.value,
        id: 0,
        medicalRecordId: this.data.medicalRecordId,
        conceptionDate: this.obstetricsForm.value.conceptionDate ? new Date(this.obstetricsForm.value.conceptionDate) : null,
        ddr: this.obstetricsForm.value.ddr ? new Date(this.obstetricsForm.value.ddr) : null
      };

      this.obstetricsRecordService.createObstetricsRecord(obstetricsRecord)
        .subscribe({
          next: (response) => {
            console.log('Obstetrics record created successfully', response);
            this.resetForm();
          },
          error: (error) => {
            console.error('Error creating obstetrics record', error);
          }
        });
    }
  }

  private resetForm() {
    this.obstetricsForm.reset({
      conceptionType: ConceptionType.Natural,
      conceptionDate: null,
      ddr: null,
      nfoetus: 1,
      comment: '',
      medicalRecordId: this.data.medicalRecordId
    });
  }

}
