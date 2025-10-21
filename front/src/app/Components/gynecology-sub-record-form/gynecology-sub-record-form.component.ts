import { Component, EventEmitter, Inject, Input, OnInit, Output } from '@angular/core';
import { CivilState, HormoneStatus } from '../../Models/enums';
import { GynecologySubRecord } from '../../Models/GynecologySubRecord';
import { GynecologySubRecordService } from '../../Services/gynecology-sub-record.service';
import { CommonModule } from '@angular/common';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-gynecology-sub-record-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './gynecology-sub-record-form.component.html',
  styleUrl: './gynecology-sub-record-form.component.css'
})
export class GynecologySubRecordFormComponent implements OnInit {
  //@Input() data: { medicalRecordId: number } = { medicalRecordId: 1 };
  @Output() formSubmitted = new EventEmitter<void>();
  gynecologyForm: FormGroup;
  civilStates = Object.values(CivilState);
  hormoneStatuses = Object.values(HormoneStatus);
  constructor(
    private fb: FormBuilder,
    private gynecologySubRecordService: GynecologySubRecordService,
    @Inject(MAT_DIALOG_DATA) public data: { medicalRecordId: number }
  ) {
    this.gynecologyForm = this.fb.group({
      work: ['', Validators.required],
      civilState: [CivilState.Married, Validators.required],
      hormoneStatus: [HormoneStatus.Undefined, Validators.required],
      menopause: [null],
      dysmenorrhea: [false],
      menorrhagia: [false],
      metrorrhagia: [false],
      periodMax: [0, [Validators.required, Validators.min(0)]],
      periodMin: [0, [Validators.required, Validators.min(0)]],
      date: [new Date(), Validators.required],
      background: [null]
    });
  }

  ngOnInit() {
    this.gynecologyForm.patchValue({
      medicalRecordId: this.data.medicalRecordId
    });
  }

  onSubmit() {
    if (this.gynecologyForm.valid) {
      const gynecologySubRecord: GynecologySubRecord = {
        ...this.gynecologyForm.value,
        id: 0,
        medicalRecordId: this.data.medicalRecordId,
        date: new Date(this.gynecologyForm.value.date),
        menopause: this.gynecologyForm.value.menopause ? new Date(this.gynecologyForm.value.menopause) : null,
        background: this.gynecologyForm.value.background ? new Date(this.gynecologyForm.value.background) : null
      };

      this.gynecologySubRecordService.createGynecologySubRecord(gynecologySubRecord)
        .subscribe({
          next: (response) => {
            console.log('Gynecology sub-record created successfully', response);
            this.formSubmitted.emit();
            this.resetForm();
          },
          error: (error) => {
            console.error('Error creating gynecology sub-record', error);
          }
        });
    }
  }

  private resetForm() {
    this.gynecologyForm.reset({
      work: '',
      civilState: '',
      hormoneStatus: HormoneStatus.Undefined,
      menopause: null,
      dysmenorrhea: false,
      menorrhagia: false,
      metrorrhagia: false,
      periodMax: 0,
      periodMin: 0,
      date: new Date().toISOString().split('T')[0],
      background: null,
      medicalRecordId: this.data.medicalRecordId
    });
  }
}
