import { Component, Input, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CivilState } from '../../Models/enums';
import { FertilitySubRecord } from '../../Models/FertilitySubRecord';
import { FertilityfubrecordService } from '../../Services/fertilityfubrecord.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-fertility-sub-record-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './fertility-sub-record-form.component.html',
  styleUrl: './fertility-sub-record-form.component.css'
})
export class FertilitySubRecordFormComponent implements OnInit {
  @Input() data: { medicalRecordId: number } = { medicalRecordId: 1 };
  fertilityForm: FormGroup;
  CivilStates = Object.values(CivilState);

  constructor(
    private fb: FormBuilder,
    private fertilitySubRecordService: FertilityfubrecordService
  ) {
    this.fertilityForm = this.fb.group({
      age: [0, [Validators.required, Validators.min(0)]],
      infertility: [null],
      duration: [''],
      cycleLength: [0, [Validators.required, Validators.min(0)]],
      cycleMin: [0, [Validators.required, Validators.min(0)]],
      cycleMax: [0, [Validators.required, Validators.min(0)]],
      dysmenorrhea: [false],
      menorrhagia: [false],
      metrorrhagia: [false],
      civilState: [CivilState.Single, Validators.required],
      comment: ['']
    });
  }

  ngOnInit() {
    this.fertilityForm.patchValue({
      medicalRecordId: this.data.medicalRecordId
    });
  }

  onSubmit() {
    if (this.fertilityForm.valid) {
      const fertilitySubRecord: FertilitySubRecord = {
        ...this.fertilityForm.value,
        id: 0,
        medicalRecordId: this.data.medicalRecordId,
        infertility: this.fertilityForm.value.infertility ? new Date(this.fertilityForm.value.infertility) : null
      };

      this.fertilitySubRecordService.createFertilitySubRecord(fertilitySubRecord)
        .subscribe({
          next: (response) => {
            console.log('Fertility sub-record created successfully', response);
            this.resetForm();
          },
          error: (error) => {
            console.error('Error creating fertility sub-record', error);
          }
        });
    }
  }

  private resetForm() {
    this.fertilityForm.reset({
      age: 0,
      infertility: null,
      duration: '',
      cycleLength: 0,
      cycleMin: 0,
      cycleMax: 0,
      dysmenorrhea: false,
      menorrhagia: false,
      metrorrhagia: false,
      civilState: CivilState.Single,
      comment: '',
      medicalRecordId: this.data.medicalRecordId
    });
  }
}
