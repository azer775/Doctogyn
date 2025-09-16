import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Biology } from '../../Models/Biology';
import { BiologyType, BiologyInterpretation } from '../../Models/BiologyEnums';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-biology-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './biology-form.component.html',
  styleUrl: './biology-form.component.css'
})
export class BiologyFormComponent implements OnInit {
  @Input() biology: Biology | null = null;
  @Output() biologySubmitted = new EventEmitter<Biology>();
  biologyForm: FormGroup;

  biologyTypes = Object.values(BiologyType);
  biologyInterpretations = Object.values(BiologyInterpretation);

  constructor(private fb: FormBuilder) {
    this.biologyForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      value: [0],
      interpretation: [null],
      comment: ['']
    });
  }

  ngOnInit(): void {
    if (this.biology) {
      this.biologyForm.patchValue({
        id: this.biology.id,
        date: this.formatDate(this.biology.date),
        type: this.biology.type,
        value: this.biology.value,
        interpretation: this.biology.interpretation,
        comment: this.biology.comment
      });
    }
  }

  submitForm(): void {
    const formValue = this.biologyForm.value;
    const biology: Biology = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      value: formValue.value,
      interpretation: formValue.interpretation,
      comment: formValue.comment,
      consultationId: this.biology ? this.biology.consultationId : 0
    };
    this.biologySubmitted.emit(biology);
  }

  getFormData(): Biology | null {
    if (this.biologyForm.valid) {
      const formValue = this.biologyForm.value;
      return {
        id: formValue.id,
        date: formValue.date ? new Date(formValue.date) : new Date(),
        type: formValue.type,
        value: formValue.value,
        interpretation: formValue.interpretation,
        comment: formValue.comment,
        consultationId: this.biology ? this.biology.consultationId : 0
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