import { Component, EventEmitter, Input, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { SpermAnalysisFormComponent } from '../sperm-analysis-form/sperm-analysis-form.component';
import { CommonModule } from '@angular/common';
import { SpermAnalysis } from '../../Models/SpermAnalysis';
import { SpermNorm } from '../../Models/SpermNorm';

@Component({
  selector: 'app-sperm-analysis-list',
  standalone: true,
  imports: [CommonModule, SpermAnalysisFormComponent],
  templateUrl: './sperm-analysis-list.component.html',
  styleUrl: './sperm-analysis-list.component.css'
})
export class SpermAnalysisListComponent implements OnInit {
  @Input() spermAnalyses: SpermAnalysis[] = [];
  @Output() spermAnalysesChange = new EventEmitter<SpermAnalysis[]>();
  @ViewChildren(SpermAnalysisFormComponent) formComponents!: QueryList<SpermAnalysisFormComponent>;

  ngOnInit(): void {
    // Ensure spermAnalyses is initialized as an array and filter out empty entries
    this.spermAnalyses = Array.isArray(this.spermAnalyses) 
      ? this.spermAnalyses.filter(sa => sa && Object.keys(sa).length > 0)
      : [];
    
    // Only emit if there are actual sperm analyses
    if (this.spermAnalyses.length > 0) {
      this.emitSpermAnalyses();
    }
  }

  addSpermAnalysisForm(): void {
    const newSpermAnalysis: SpermAnalysis = {
      id: 0,
      date: new Date(),
      abstinence: 0,
      ph: 0,
      volume: 0,
      concentration: 0,
      progressivemobility: 0,
      totalmotility: 0,
      totalcount: 0,
      roundcells: 0,
      leukocytes: 0,
      morphology: 0,
      norm: Object.values(SpermNorm)[0] || null,
      vitality: 0,
      tms: 0,
      consultationId: 0
    };
    this.spermAnalyses = [...this.spermAnalyses, newSpermAnalysis];
    this.emitSpermAnalyses();
  }

  deleteSpermAnalysisForm(index: number): void {
    this.spermAnalyses = this.spermAnalyses.filter((_, i) => i !== index);
    this.emitSpermAnalyses();
  }

  updateSpermAnalysis(updatedSpermAnalysis: SpermAnalysis, index: number): void {
    this.spermAnalyses = this.spermAnalyses.map((spermAnalysis, i) =>
      i === index ? { ...updatedSpermAnalysis, consultationId: 0, id: 0 } : spermAnalysis
    );
    this.emitSpermAnalyses();
  }

  private emitSpermAnalyses(): void {
    // Emit spermAnalyses without consultationId and id
    const spermAnalysesToEmit = this.spermAnalyses.map(spermAnalysis => ({
      date: spermAnalysis.date,
      abstinence: spermAnalysis.abstinence,
      ph: spermAnalysis.ph,
      volume: spermAnalysis.volume,
      concentration: spermAnalysis.concentration,
      progressivemobility: spermAnalysis.progressivemobility,
      totalmotility: spermAnalysis.totalmotility,
      totalcount: spermAnalysis.totalcount,
      roundcells: spermAnalysis.roundcells,
      leukocytes: spermAnalysis.leukocytes,
      morphology: spermAnalysis.morphology,
      norm: spermAnalysis.norm,
      vitality: spermAnalysis.vitality,
      tms: spermAnalysis.tms,
      consultationId: 0,
      id: 0
    }));
    this.spermAnalysesChange.emit(spermAnalysesToEmit);
  }

  getCurrentFormData(): SpermAnalysis[] {
    const currentData: SpermAnalysis[] = [];
    this.formComponents.forEach(form => {
      const formData = form.getFormData();
      if (formData) {
        currentData.push(formData);
      }
    });
    return currentData;
  }
}
