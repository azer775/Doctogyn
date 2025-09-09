import { Component, Input, OnInit } from '@angular/core';
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
}