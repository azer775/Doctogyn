import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BloodGroup } from '../../Models/BloodGroup';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { BloodType } from '../../Models/BloodType';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-blood-group-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './blood-group-form.component.html',
  styleUrl: './blood-group-form.component.css'
})
export class BloodGroupFormComponent implements OnInit {
  @Input() bloodGroup: BloodGroup | null = null;
  @Output() bloodGroupSubmitted = new EventEmitter<BloodGroup>();
  bloodGroupForm: FormGroup;

  bloodTypes = Object.values(BloodType);

  constructor(private fb: FormBuilder) {
    this.bloodGroupForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      comment: ['']
    });
  }

  ngOnInit(): void {
    if (this.bloodGroup) {
      this.bloodGroupForm.patchValue({
        id: this.bloodGroup.id,
        date: this.formatDate(this.bloodGroup.date),
        type: this.bloodGroup.type,
        comment: this.bloodGroup.comment
      });
    }
  }

  submitForm(): void {
    const formValue = this.bloodGroupForm.value;
    const bloodGroup: BloodGroup = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      comment: formValue.comment,
      consultationId: this.bloodGroup ? this.bloodGroup.consultationId : 0
    };
    this.bloodGroupSubmitted.emit(bloodGroup);
  }

  getFormData(): BloodGroup | null {
    if (this.bloodGroupForm.valid) {
      const formValue = this.bloodGroupForm.value;
      return {
        id: formValue.id,
        date: formValue.date ? new Date(formValue.date) : new Date(),
        type: formValue.type,
        comment: formValue.comment,
        consultationId: this.bloodGroup ? this.bloodGroup.consultationId : 0
      };
    }
    return null;
  }

  private formatDate(date: Date | string): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toISOString().split('T')[0];
  }
}
