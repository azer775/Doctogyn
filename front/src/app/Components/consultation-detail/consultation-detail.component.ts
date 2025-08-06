import { Component, Input, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { Consultation } from '../../Models/Consultation';
import { ConsultationType, Status } from '../../Models/enums';
import { ConsultationService } from '../../Services/consultation.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consultation-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './consultation-detail.component.html',
  styleUrl: './consultation-detail.component.css'
})
export class ConsultationDetailComponent implements OnInit {
  @Input() consultationId!: number;
  consultation: Consultation | null = null;
  error: string | null = null;
  ConsultationTypes = Object.values(ConsultationType);
  Statuses = Object.values(Status);

  constructor(private consultationService: ConsultationService) {}

  ngOnInit() {
    if (this.consultationId) {
      this.consultationService.getConsultation(this.consultationId)
        .pipe(
          catchError(err => {
            this.error = 'Failed to load consultation: ' + err.message;
            return of(null);
          })
        )
        .subscribe(record => {
          this.consultation = record;
        });
    } else {
      this.error = 'No consultation ID provided';
    }
  }

}
