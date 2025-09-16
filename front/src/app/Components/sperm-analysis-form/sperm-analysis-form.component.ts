import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { SpermAnalysis } from '../../Models/SpermAnalysis';
import { SpermNorm } from '../../Models/SpermNorm';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sperm-analysis-form',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './sperm-analysis-form.component.html',
  styleUrl: './sperm-analysis-form.component.css'
})
export class SpermAnalysisFormComponent implements OnInit {
  @Input() spermAnalysis: SpermAnalysis | null = null;
  @Output() spermAnalysisSubmitted = new EventEmitter<SpermAnalysis>();
  spermAnalysisForm: FormGroup;

  spermNorms = Object.values(SpermNorm);

  constructor(private fb: FormBuilder) {
    this.spermAnalysisForm = this.fb.group({
      date: [''],
      abstinence: [0],
      ph: [0],
      volume: [0],
      concentration: [0],
      progressivemobility: [0],
      totalmotility: [0],
      totalcount: [0],
      roundcells: [0],
      leukocytes: [0],
      morphology: [0],
      norm: [null],
      vitality: [0],
      tms: [0],
      comment: ['']
    });
  }

  ngOnInit(): void {
    if (this.spermAnalysis) {
      this.spermAnalysisForm.patchValue({
        date: this.formatDate(this.spermAnalysis.date),
        abstinence: this.spermAnalysis.abstinence,
        ph: this.spermAnalysis.ph,
        volume: this.spermAnalysis.volume,
        concentration: this.spermAnalysis.concentration,
        progressivemobility: this.spermAnalysis.progressivemobility,
        totalmotility: this.spermAnalysis.totalmotility,
        totalcount: this.spermAnalysis.totalcount,
        roundcells: this.spermAnalysis.roundcells,
        leukocytes: this.spermAnalysis.leukocytes,
        morphology: this.spermAnalysis.morphology,
        norm: this.spermAnalysis.norm,
        vitality: this.spermAnalysis.vitality,
        tms: this.spermAnalysis.tms
      });
    }
  }

  submitForm(): void {
    const formValue = this.spermAnalysisForm.value;
    const spermAnalysis: SpermAnalysis = {
      id: 0, // Default value, as id is excluded from form
      date: formValue.date ? new Date(formValue.date) : new Date(),
      abstinence: formValue.abstinence,
      ph: formValue.ph,
      volume: formValue.volume,
      concentration: formValue.concentration,
      progressivemobility: formValue.progressivemobility,
      totalmotility: formValue.totalmotility,
      totalcount: formValue.totalcount,
      roundcells: formValue.roundcells,
      leukocytes: formValue.leukocytes,
      morphology: formValue.morphology,
      norm: formValue.norm,
      vitality: formValue.vitality,
      tms: formValue.tms,
      consultationId: 0 // Default value, as consultationId is excluded
    };
    this.spermAnalysisSubmitted.emit(spermAnalysis);
  }

  getFormData(): SpermAnalysis | null {
    if (this.spermAnalysisForm.valid) {
      const formValue = this.spermAnalysisForm.value;
      return {
        id: 0, // Default value, as id is excluded from form
        date: formValue.date ? new Date(formValue.date) : new Date(),
        abstinence: formValue.abstinence,
        ph: formValue.ph,
        volume: formValue.volume,
        concentration: formValue.concentration,
        progressivemobility: formValue.progressivemobility,
        totalmotility: formValue.totalmotility,
        totalcount: formValue.totalcount,
        roundcells: formValue.roundcells,
        leukocytes: formValue.leukocytes,
        morphology: formValue.morphology,
        norm: formValue.norm,
        vitality: formValue.vitality,
        tms: formValue.tms,
        consultationId: this.spermAnalysis ? this.spermAnalysis.consultationId : 0
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