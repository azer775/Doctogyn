import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { RadiologyFormComponent } from '../radiology-form/radiology-form.component';
import { Radiology } from '../../Models/Radiology';
import { RadiologyType } from '../../Models/RadiologyType';

@Component({
  selector: 'app-radiology-list',
  standalone: true,
  imports: [CommonModule,RadiologyFormComponent],
  templateUrl: './radiology-list.component.html',
  styleUrl: './radiology-list.component.css'
})
export class RadiologyListComponent implements OnInit {
  @Input() radiologies: Radiology[] = [];
  @Output() radiologiesChange = new EventEmitter<Radiology[]>();

  ngOnInit(): void {
    // Ensure radiologies is initialized as an array
    this.radiologies = Array.isArray(this.radiologies) ? [...this.radiologies] : [];
    this.emitRadiologies();
  }

  addRadiologyForm(): void {
    const newRadiology: Radiology = {
      id: 0,
      date: new Date(),
      type: Object.values(RadiologyType)[0] || null,
      conclusion: '',
      comment: '',
      consultationId: 0
    };
    this.radiologies = [...this.radiologies, newRadiology];
    this.emitRadiologies();
  }

  deleteRadiologyForm(index: number): void {
    this.radiologies = this.radiologies.filter((_, i) => i !== index);
    this.emitRadiologies();
  }

  updateRadiology(updatedRadiology: Radiology, index: number): void {
    this.radiologies = this.radiologies.map((radiology, i) =>
      i === index ? { ...updatedRadiology, consultationId: 0, id: 0 } : radiology
    );
    this.emitRadiologies();
  }

  private emitRadiologies(): void {
    // Emit radiologies without consultationId and id
    const radiologiesToEmit = this.radiologies.map(radiology => ({
      date: radiology.date,
      type: radiology.type,
      conclusion: radiology.conclusion,
      comment: radiology.comment,
      consultationId: 0,
      id: 0
    }));
    this.radiologiesChange.emit(radiologiesToEmit);
  }
}