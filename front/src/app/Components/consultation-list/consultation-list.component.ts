import { Component, Input, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { Consultation } from '../../Models/Consultation';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consultation-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultation-list.component.html',
  styleUrl: './consultation-list.component.css'
})
export class ConsultationListComponent implements OnInit {
  @Input() consultations: Consultation[] | null = null;
  @Input() setSelectedConsultationId: ((id: number | null) => void) | null = null;
  @Input() setSelectedEditConsultationId: ((id: number | null) => void) | null = null;
  showDetails: { [key: number]: boolean } = {};
  showEdit: { [key: number]: boolean } = {};

  constructor(private consultationService: ConsultationService) {}
  ngOnInit() {
    if (this.consultations) {
      this.consultations.forEach(record => {
        this.showDetails[record.id] = false;
        this.showEdit[record.id] = false;
      });
    }
  }

  toggleDetails(id: number) {
    const isCurrentlySelected = this.showDetails[id];
    Object.keys(this.showDetails).forEach(key => {
      if (+key !== id) {
        this.showDetails[+key] = false;
      }
    });
    this.showDetails[id] = !isCurrentlySelected;
    if (this.setSelectedConsultationId) {
      this.setSelectedConsultationId(this.showDetails[id] ? id : null);
    }
    // Reset edit state when showing details
    Object.keys(this.showEdit).forEach(key => {
      this.showEdit[+key] = false;
    });
    if (this.setSelectedEditConsultationId) {
      this.setSelectedEditConsultationId(null);
    }
  }

  editConsultation(id: number) {
    const isCurrentlyEditing = this.showEdit[id];
    Object.keys(this.showEdit).forEach(key => {
      if (+key !== id) {
        this.showEdit[+key] = false;
      }
    });
    this.showEdit[id] = !isCurrentlyEditing;
    if (this.setSelectedEditConsultationId) {
      this.setSelectedEditConsultationId(this.showEdit[id] ? id : null);
    }
    // Reset details state when editing
    Object.keys(this.showDetails).forEach(key => {
      this.showDetails[+key] = false;
    });
    if (this.setSelectedConsultationId) {
      this.setSelectedConsultationId(null);
    }
  }
  deleteConsultation(id: number){
    this.consultationService.deleteConsultation(id).subscribe({
      next: () => {
        // Remove the deleted consultation from the list
        this.consultations = this.consultations?.filter(c => c.id !== id) || null;
      },
      error: (err) => {
        console.error('Error deleting consultation:', err);
      }
    });
  }
}
