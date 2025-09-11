import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { SpermAnalysisListComponent } from '../sperm-analysis-list/sperm-analysis-list.component';
import { CommonModule } from '@angular/common';
import { BacteriologyListComponent } from '../bacteriology-list/bacteriology-list.component';
import { BiologyListComponent } from '../biology-list/biology-list.component';
import { BloodGroupListComponent } from '../blood-group-list/blood-group-list.component';
import { RadiologyListComponent } from '../radiology-list/radiology-list.component';
import { SerologyListComponent } from '../serology-list/serology-list.component';
import { Bacteriology } from '../../Models/Bacteriology';
import { Biology } from '../../Models/Biology';
import { BloodGroup } from '../../Models/BloodGroup';
import { ExtractionResponse } from '../../Models/ExtractionResponse';
import { Radiology } from '../../Models/Radiology';
import { Serology } from '../../Models/Serology';
import { SpermAnalysis } from '../../Models/SpermAnalysis';

@Component({
  selector: 'app-analyses-list',
  standalone: true,
  imports: [CommonModule, BiologyListComponent, BacteriologyListComponent, BloodGroupListComponent, RadiologyListComponent, SerologyListComponent, SpermAnalysisListComponent],
  templateUrl: './analyses-list.component.html',
  styleUrl: './analyses-list.component.css'
})
export class AnalysesListComponent implements OnInit, OnChanges {
  @Input() extractionResponse: ExtractionResponse | null = null;

  biologies: Biology[] = [];
  bacteriologies: Bacteriology[] = [];
  bloodGroups: BloodGroup[] = [];
  radiologies: Radiology[] = [];
  serologies: Serology[] = [];
  spermAnalyses: SpermAnalysis[] = [];

  visibility = {
    biology: false,
    bacteriology: false,
    bloodGroup: false,
    radiology: false,
    serology: false,
    spermAnalysis: false
  };

  ngOnInit(): void {
    this.processExtractionResponse();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['extractionResponse']) {
      this.processExtractionResponse();
    }
  }

  private processExtractionResponse(): void {
    console.log('AnalysesListComponent - Processing extraction response:', this.extractionResponse);
    
    if (this.extractionResponse) {
      this.biologies = this.extractionResponse.documents.flatMap(d => d.biologies) || [];
      this.bacteriologies = this.extractionResponse.documents.flatMap(d => d.bacteriologies) || [];
      this.bloodGroups = this.extractionResponse.documents.flatMap(d => d.bloodgroups) || [];
      this.radiologies = this.extractionResponse.documents.flatMap(d => d.radiologies) || [];
      this.serologies = this.extractionResponse.documents.flatMap(d => d.serologies) || [];
      this.spermAnalyses = this.extractionResponse.documents.flatMap(d => d.spermAnalyses) || [];
      
      // Automatically show sections that have data
      this.autoShowSectionsWithData();
      
      console.log('AnalysesListComponent - Processed data:', {
        biologies: this.biologies,
        bacteriologies: this.bacteriologies,
        bloodGroups: this.bloodGroups,
        radiologies: this.radiologies,
        serologies: this.serologies,
        spermAnalyses: this.spermAnalyses
      });
    } else {
      console.log('AnalysesListComponent - No extraction response provided');
    }
  }

  private autoShowSectionsWithData(): void {
    this.visibility.biology = this.biologies.length > 0;
    this.visibility.bacteriology = this.bacteriologies.length > 0;
    this.visibility.bloodGroup = this.bloodGroups.length > 0;
    this.visibility.radiology = this.radiologies.length > 0;
    this.visibility.serology = this.serologies.length > 0;
    this.visibility.spermAnalysis = this.spermAnalyses.length > 0;
    
    console.log('AnalysesListComponent - Auto-showing sections with data:', this.visibility);
  }

  toggle(key: keyof typeof this.visibility): void {
    this.visibility[key] = !this.visibility[key];
  }

  hasVisibleLists(): boolean {
    return Object.values(this.visibility).some(visible => visible);
  }
}
