import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Bacteriology } from '../../Models/Bacteriology';
import { BacteriologyType, BacteriologyInterpretation } from '../../Models/BacteriologyEnums';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BacteriologyFormComponent } from '../bacteriology-form/bacteriology-form.component';

@Component({
  selector: 'app-bacteriology-list',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, BacteriologyFormComponent],
  templateUrl: './bacteriology-list.component.html',
  styleUrl: './bacteriology-list.component.css'
})
export class BacteriologyListComponent implements OnInit {
  @Input() bacteriologies: Bacteriology[] = [];
  @Output() bacteriologiesChange = new EventEmitter<Bacteriology[]>();

  ngOnInit(): void {
    // Ensure bacteriologies is initialized as an array
    this.bacteriologies = Array.isArray(this.bacteriologies) ? [...this.bacteriologies] : [];
    this.emitBacteriologies();
  }

  addBacteriologyForm(): void {
    const newBacteriology: Bacteriology = {
      id: 0,
      date: new Date(),
      type: Object.values(BacteriologyType)[0] || null,
      germs: [],
      interpretation: Object.values(BacteriologyInterpretation)[0] || null,
      comment: '',
      consultationId: 0
    };
    this.bacteriologies = [...this.bacteriologies, newBacteriology];
    this.emitBacteriologies();
  }

  deleteBacteriologyForm(index: number): void {
    this.bacteriologies = this.bacteriologies.filter((_, i) => i !== index);
    this.emitBacteriologies();
  }

  updateBacteriology(updatedBacteriology: Bacteriology, index: number): void {
    this.bacteriologies = this.bacteriologies.map((bacteriology, i) =>
      i === index ? { ...updatedBacteriology, consultationId: 0, id: 0 } : bacteriology
    );
    this.emitBacteriologies();
  }

  private emitBacteriologies(): void {
    // Emit bacteriologies without consultationId and id
    const bacteriologiesToEmit = this.bacteriologies.map(bacteriology => ({
      date: bacteriology.date,
      type: bacteriology.type,
      germs: bacteriology.germs,
      interpretation: bacteriology.interpretation,
      comment: bacteriology.comment,
      consultationId: 0,
      id: 0
    }));
    this.bacteriologiesChange.emit(bacteriologiesToEmit);
  }
}
