import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Radiology } from '../../Models/Radiology';
import { RadiologyType } from '../../Models/RadiologyType';
import { comment } from 'postcss';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-radiology-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './radiology-form.component.html',
  styleUrl: './radiology-form.component.css'
})
export class RadiologyFormComponent implements OnInit {
  @Input() radiology: Radiology | null = null;
  @Output() radiologySubmitted = new EventEmitter<Radiology>();
  radiologyForm: FormGroup;

  radiologyTypes = Object.values(RadiologyType);

  constructor(private fb: FormBuilder) {
    this.radiologyForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      conclusion: ['']
    });
  }

  ngOnInit(): void {
    if (this.radiology) {
      this.radiologyForm.patchValue({
        id: this.radiology.id,
        date: this.formatDate(this.radiology.date),
        type: this.radiology.type,
        comment: this.radiology.comment,
        conclusion: this.radiology.conclusion
      });
    }
  }

  submitForm(): void {
    const formValue = this.radiologyForm.value;
    const radiology: Radiology = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      comment: formValue.comment,
      conclusion: formValue.conclusion,
      consultationId: this.radiology ? this.radiology.consultationId : 0
    };
    this.radiologySubmitted.emit(radiology);
  }

  private formatDate(date: Date | string): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toISOString().split('T')[0];
  }
}