import { Component, Input, OnInit, ViewChild, Output, EventEmitter } from '@angular/core';
import { Bacteriology } from '../../Models/Bacteriology';
import { Biology } from '../../Models/Biology';
import { BloodGroup } from '../../Models/BloodGroup';
import { ExtractionResponse } from '../../Models/ExtractionResponse';
import { Radiology } from '../../Models/Radiology';
import { Serology } from '../../Models/Serology';
import { SpermAnalysis } from '../../Models/SpermAnalysis';
import { CommonModule } from '@angular/common';
import { BacteriologyListComponent } from '../bacteriology-list/bacteriology-list.component';
import { BiologyListComponent } from '../biology-list/biology-list.component';
import { BloodGroupListComponent } from '../blood-group-list/blood-group-list.component';
import { RadiologyListComponent } from '../radiology-list/radiology-list.component';
import { SerologyListComponent } from '../serology-list/serology-list.component';
import { SpermAnalysisListComponent } from '../sperm-analysis-list/sperm-analysis-list.component';

@Component({
  selector: 'app-email-analyses-list',
  standalone: true,
  imports: [CommonModule,
    BiologyListComponent,
    BacteriologyListComponent,
    BloodGroupListComponent,
    RadiologyListComponent,
    SerologyListComponent,
    SpermAnalysisListComponent],
  templateUrl: './email-analyses-list.component.html',
  styleUrl: './email-analyses-list.component.css'
})
export class EmailAnalysesListComponent implements OnInit {
  @Input() extractionResponse: ExtractionResponse | null = null;
  @Output() allAnalysesCollected = new EventEmitter<{
    biologies: Biology[],
    bacteriologies: Bacteriology[],
    bloodGroups: BloodGroup[],
    radiologies: Radiology[],
    serologies: Serology[],
    spermAnalyses: SpermAnalysis[]
  }>();

  // ViewChild references to access child list components
  @ViewChild(BiologyListComponent) biologyListComponent?: BiologyListComponent;
  @ViewChild(BacteriologyListComponent) bacteriologyListComponent?: BacteriologyListComponent;
  @ViewChild(BloodGroupListComponent) bloodGroupListComponent?: BloodGroupListComponent;
  @ViewChild(RadiologyListComponent) radiologyListComponent?: RadiologyListComponent;
  @ViewChild(SerologyListComponent) serologyListComponent?: SerologyListComponent;
  @ViewChild(SpermAnalysisListComponent) spermAnalysisListComponent?: SpermAnalysisListComponent;

  biologies: Biology[] = [];
  bacteriologies: Bacteriology[] = [];
  bloodGroups: BloodGroup[] = [];
  radiologies: Radiology[] = [];
  serologies: Serology[] = [];
  spermAnalyses: SpermAnalysis[] = [];

  ngOnInit(): void {
    if (this.extractionResponse) {
      this.biologies = this.extractionResponse.documents
        .flatMap(d => d.biologies || [])
        .filter(b => b && Object.keys(b).length > 0);
      
      this.bacteriologies = this.extractionResponse.documents
        .flatMap(d => d.bacteriologies || [])
        .filter(b => b && Object.keys(b).length > 0);
      
      this.bloodGroups = this.extractionResponse.documents
        .flatMap(d => d.bloodgroups || [])
        .filter(bg => bg && Object.keys(bg).length > 0);
      
      this.radiologies = this.extractionResponse.documents
        .flatMap(d => d.radiologies || [])
        .filter(r => r && Object.keys(r).length > 0);
      
      this.serologies = this.extractionResponse.documents
        .flatMap(d => d.serologies || [])
        .filter(s => s && Object.keys(s).length > 0);
      
      this.spermAnalyses = this.extractionResponse.documents
        .flatMap(d => d.spermAnalyses || [])
        .filter(sa => sa && Object.keys(sa).length > 0);
    }
  }

  hasAnyAnalyses(): boolean {
    return this.biologies.length > 0 || 
           this.bacteriologies.length > 0 || 
           this.bloodGroups.length > 0 || 
           this.radiologies.length > 0 || 
           this.serologies.length > 0 || 
           this.spermAnalyses.length > 0;
  }

  /**
   * Collects all current data from the analysis forms
   * This method gathers data from all child list components
   */
  collectAllAnalyses(): void {
    const collectedData = {
      biologies: this.biologyListComponent?.getCurrentFormData() || [],
      bacteriologies: this.bacteriologyListComponent?.getCurrentFormData() || [],
      bloodGroups: this.bloodGroupListComponent?.getCurrentFormData() || [],
      radiologies: this.radiologyListComponent?.getCurrentFormData() || [],
      serologies: this.serologyListComponent?.getCurrentFormData() || [],
      spermAnalyses: this.spermAnalysisListComponent?.getCurrentFormData() || []
    };

    // Emit the collected data to parent component
    this.allAnalysesCollected.emit(collectedData);
    
    // Log for debugging
    console.log('Collected all analyses data:', collectedData);
    
    // Optional: Show success message
    alert(`Collected data: ${this.getTotalAnalysesCount(collectedData)} analyses`);
  }

  /**
   * Counts total number of analyses across all types
   */
  private getTotalAnalysesCount(data: any): number {
    return (data.biologies?.length || 0) +
           (data.bacteriologies?.length || 0) +
           (data.bloodGroups?.length || 0) +
           (data.radiologies?.length || 0) +
           (data.serologies?.length || 0) +
           (data.spermAnalyses?.length || 0);
  }
}