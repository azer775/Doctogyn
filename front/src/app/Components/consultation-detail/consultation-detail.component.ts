import { Component, Input, OnChanges, OnInit, SimpleChanges, Output, EventEmitter } from '@angular/core';
import { catchError, of } from 'rxjs';
import { Consultation } from '../../Models/Consultation';
import { ConsultationType, Status } from '../../Models/enums';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';
import { Biology } from '../../Models/Biology';
import { Serology } from '../../Models/Serology';
import { Bacteriology } from '../../Models/Bacteriology';
import { SpermAnalysis } from '../../Models/SpermAnalysis';
import { BloodGroup } from '../../Models/BloodGroup';
import { Radiology } from '../../Models/Radiology';

@Component({
  selector: 'app-consultation-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultation-detail.component.html',
  styleUrl: './consultation-detail.component.css'
})
export class ConsultationDetailComponent implements OnInit , OnChanges {
  @Input() consultationId!: number;
  @Output() closeDetail = new EventEmitter<void>();
  consultation: Consultation | null = null;
  error: string | null = null;
  ConsultationTypes = Object.values(ConsultationType);
  Statuses = Object.values(Status);
  selectedEchographyIndex: number = 0;

  constructor(private consultationService: ConsultationService) {}

  ngOnInit() {
    if (this.consultationId) {
      this.consultationService.getConsultation(this.consultationId)
        .pipe(
          catchError(err => {
            this.error = 'Failed to load consultation: ' + err.message;
            return of(null);
          })
        )
        .subscribe(record => {
          this.consultation = record;
        });
    } else {
      this.error = 'No consultation ID provided';
    }
  }
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['consultationId'] && changes['consultationId'].currentValue) {
      this.consultationId = changes['consultationId'].currentValue;
      this.ngOnInit();
    }
  }

  onClose() {
    this.closeDetail.emit();
  }

  // Helper methods to extract analyses from extractionAnalyses
  hasAnalyses(): boolean {
    if (!this.consultation?.extractionAnalyses?.documents) return false;
    const docs = this.consultation.extractionAnalyses.documents;
    return docs.some(doc => 
      (doc.biologies && doc.biologies.length > 0) ||
      (doc.serologies && doc.serologies.length > 0) ||
      (doc.bacteriologies && doc.bacteriologies.length > 0) ||
      (doc.spermAnalyses && doc.spermAnalyses.length > 0) ||
      (doc.bloodgroups && doc.bloodgroups.length > 0) ||
      (doc.radiologies && doc.radiologies.length > 0)
    );
  }

  getBiologies(): Biology[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.biologies || []);
  }

  getSerologies(): Serology[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.serologies || []);
  }

  getBacteriologies(): Bacteriology[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.bacteriologies || []);
  }

  getSpermAnalyses(): SpermAnalysis[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.spermAnalyses || []);
  }

  getBloodGroups(): BloodGroup[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.bloodgroups || []);
  }

  getRadiologies(): Radiology[] {
    if (!this.consultation?.extractionAnalyses?.documents) return [];
    return this.consultation.extractionAnalyses.documents.flatMap(doc => doc.radiologies || []);
  }

  selectEchography(index: number): void {
    this.selectedEchographyIndex = index;
  }

}
