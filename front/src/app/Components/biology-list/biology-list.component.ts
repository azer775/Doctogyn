import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Biology } from '../../Models/Biology';
import { BiologyType, BiologyInterpretation } from '../../Models/BiologyEnums';
import { comment } from 'postcss';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BiologyFormComponent } from '../biology-form/biology-form.component';

@Component({
  selector: 'app-biology-list',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule, BiologyFormComponent],
  templateUrl: './biology-list.component.html',
  styleUrl: './biology-list.component.css'
})
export class BiologyListComponent implements OnInit {
  @Input() biologies: Biology[] = [];
  @Output() biologiesChange = new EventEmitter<Biology[]>();

  ngOnInit(): void {
    // Ensure biologies is initialized as an array
    this.biologies = Array.isArray(this.biologies) ? [...this.biologies] : [];
    this.emitBiologies();
  }

  addBiologyForm(): void {
    const newBiology: Biology = {
      id: 0,
      date: new Date(),
      type: Object.values(BiologyType)[0] || null,
      value: 0,
      interpretation: Object.values(BiologyInterpretation)[0] || null,
      comment: '',
      consultationId: 0
    };
    this.biologies = [...this.biologies, newBiology];
    this.emitBiologies();
  }

  deleteBiologyForm(index: number): void {
    this.biologies = this.biologies.filter((_, i) => i !== index);
    this.emitBiologies();
  }

  updateBiology(updatedBiology: Biology, index: number): void {
    this.biologies = this.biologies.map((biology, i) =>
      i === index ? { ...updatedBiology, consultationId: 0 } : biology
    );
    this.emitBiologies();
  }

  private emitBiologies(): void {
    // Emit biologies without consultationId
    const biologiesToEmit = this.biologies.map(biology => ({
      id: biology.id,
      date: biology.date,
      type: biology.type,
      value: biology.value,
      interpretation: biology.interpretation,
      comment: biology.comment,
      consultationId: biology.consultationId
    }));
    this.biologiesChange.emit(biologiesToEmit);
  }
}
