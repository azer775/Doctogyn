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
  showDetails: { [key: number]: boolean } = {};

  ngOnInit() {
    if (this.consultations) {
      this.consultations.forEach(record => {
        this.showDetails[record.id] = false;
      });
    }
  }

  toggleDetails(id: number) {
    this.showDetails[id] = !this.showDetails[id];
    // Call the callback to update the selected consultation ID
    if (this.setSelectedConsultationId) {
      this.setSelectedConsultationId(this.showDetails[id] ? id : null);
    }
  }
}
