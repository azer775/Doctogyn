import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Serology } from '../../Models/Serology';
import { SerologyType, SerologyInterpretation } from '../../Models/SerologyEnums';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-serology-form',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './serology-form.component.html',
  styleUrl: './serology-form.component.css'
})
export class SerologyFormComponent implements OnInit {
  @Input() serology: Serology | null = null;
  @Output() serologySubmitted = new EventEmitter<Serology>();
  serologyForm: FormGroup;

  serologyTypes = Object.values(SerologyType);
  serologyInterpretations = Object.values(SerologyInterpretation);

  constructor(private fb: FormBuilder) {
    this.serologyForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      interpretation: [null],
      comment: ['']
    });
  }

  ngOnInit(): void {
    if (this.serology) {
      this.serologyForm.patchValue({
        id: this.serology.id,
        date: this.formatDate(this.serology.date),
        type: this.serology.type,
        value: this.serology.value,
        interpretation: this.serology.interpretation,
        comment: this.serology.comment
      });
    }
  }

  submitForm(): void {
    const formValue = this.serologyForm.value;
    const serology: Serology = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      value: formValue.value,
      interpretation: formValue.interpretation,
      comment: formValue.comment,
      consultationId: this.serology ? this.serology.consultationId : 0
    };
    this.serologySubmitted.emit(serology);
  }

  private formatDate(date: Date | string): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toISOString().split('T')[0];
  }
}
