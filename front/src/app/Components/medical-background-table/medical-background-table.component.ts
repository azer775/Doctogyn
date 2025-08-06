import { Component, Input } from '@angular/core';
import { MedicalBackground } from '../../Models/MedicalBackground';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-medical-background-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './medical-background-table.component.html',
  styleUrl: './medical-background-table.component.css'
})
export class MedicalBackgroundTableComponent {
  @Input() medicalBackgroundList: MedicalBackground[] = [];
  backgroundTypes: string[] = ['Familial', 'Allergies', 'Medical', 'Chirurgical'];

  getRecordsByType(type: string): MedicalBackground[] {
    return this.medicalBackgroundList.filter(record => record.backgroundType === type);
  }

}
