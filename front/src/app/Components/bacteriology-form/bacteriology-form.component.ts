import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Bacteriology } from '../../Models/Bacteriology';
import { BacteriologyType, Germ, BacteriologyInterpretation } from '../../Models/BacteriologyEnums';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bacteriology-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './bacteriology-form.component.html',
  styleUrl: './bacteriology-form.component.css'
})
export class BacteriologyFormComponent implements OnInit {
  @Input() bacteriology: Bacteriology | null = null;
  @Output() bacteriologySubmitted = new EventEmitter<Bacteriology>();
  bacteriologyForm: FormGroup;

  bacteriologyTypes = Object.values(BacteriologyType);
  germs = Object.values(Germ);
  bacteriologyInterpretations = Object.values(BacteriologyInterpretation);

  constructor(private fb: FormBuilder) {
    this.bacteriologyForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      germs: [[]],
      interpretation: [null],
      comment: [''],
      consultationId: [0]
    });
  }

  ngOnInit(): void {
    if (this.bacteriology) {
      this.bacteriologyForm.patchValue({
        id: this.bacteriology.id,
        date: this.formatDate(this.bacteriology.date),
        type: this.bacteriology.type,
        germs: this.bacteriology.germs,
        interpretation: this.bacteriology.interpretation,
        comment: this.bacteriology.comment,
        consultationId: this.bacteriology.consultationId
      });
    }
  }

  // Emit the form's Bacteriology data
  submitForm(): void {
    const formValue = this.bacteriologyForm.value;
    const bacteriology: Bacteriology = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      germs: formValue.germs,
      interpretation: formValue.interpretation,
      comment: formValue.comment,
      consultationId: formValue.consultationId
    };
    this.bacteriologySubmitted.emit(bacteriology);
  }

  // Format Date to YYYY-MM-DD for input type="date"
  private formatDate(date: Date | string): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toISOString().split('T')[0];
  }
}
