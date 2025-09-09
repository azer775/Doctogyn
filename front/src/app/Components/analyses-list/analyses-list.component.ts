import { Component, Input, OnInit } from '@angular/core';
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
export class AnalysesListComponent implements OnInit {
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
    if (this.extractionResponse) {
      this.biologies = this.extractionResponse.documents.flatMap(d => d.biologies);
      this.bacteriologies = this.extractionResponse.documents.flatMap(d => d.bacteriologies);
      this.bloodGroups = this.extractionResponse.documents.flatMap(d => d.bloodgroups);
      this.radiologies = this.extractionResponse.documents.flatMap(d => d.radiologies);
      this.serologies = this.extractionResponse.documents.flatMap(d => d.serologies);
      this.spermAnalyses = this.extractionResponse.documents.flatMap(d => d.spermAnalyses);
    }
  }

  toggle(key: keyof typeof this.visibility): void {
    this.visibility[key] = !this.visibility[key];
  }

  hasVisibleLists(): boolean {
    return Object.values(this.visibility).some(visible => visible);
  }
}
