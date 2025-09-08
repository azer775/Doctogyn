import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SerologyFormComponent } from '../serology-form/serology-form.component';
import { CommonModule } from '@angular/common';
import { Serology } from '../../Models/Serology';
import { SerologyType, SerologyInterpretation } from '../../Models/SerologyEnums';

@Component({
  selector: 'app-serology-list',
  standalone: true,
  imports: [CommonModule, SerologyFormComponent],
  templateUrl: './serology-list.component.html',
  styleUrl: './serology-list.component.css'
})
export class SerologyListComponent implements OnInit {
  @Input() serologies: Serology[] = [];
  @Output() serologiesChange = new EventEmitter<Serology[]>();

  ngOnInit(): void {
    // Ensure serologies is initialized as an array
    this.serologies = Array.isArray(this.serologies) ? [...this.serologies] : [];
    this.emitSerologies();
  }

  addSerologyForm(): void {
    const newSerology: Serology = {
      id: 0,
      date: new Date(),
      type: Object.values(SerologyType)[0] || null,
      interpretation: Object.values(SerologyInterpretation)[0] || null,
      value: 0,
      comment: '',
      consultationId: 0
    };
    this.serologies = [...this.serologies, newSerology];
    this.emitSerologies();
  }

  deleteSerologyForm(index: number): void {
    this.serologies = this.serologies.filter((_, i) => i !== index);
    this.emitSerologies();
  }

  updateSerology(updatedSerology: Serology, index: number): void {
    this.serologies = this.serologies.map((serology, i) =>
      i === index ? { ...updatedSerology, consultationId: 0, id: 0 } : serology
    );
    this.emitSerologies();
  }

  private emitSerologies(): void {
    // Emit serologies without consultationId and id
    const serologiesToEmit = this.serologies.map(serology => ({
      date: serology.date,
      type: serology.type,
      value: serology.value,
      interpretation: serology.interpretation,
      comment: serology.comment,
      consultationId: 0,
      id: 0
    }));
    this.serologiesChange.emit(serologiesToEmit);
  }
}
