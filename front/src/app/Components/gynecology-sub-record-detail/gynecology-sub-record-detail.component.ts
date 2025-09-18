import { Component, Input, OnInit, signal } from '@angular/core';
import { catchError, of } from 'rxjs';
import { GynecologySubRecord } from '../../Models/GynecologySubRecord';
import { GynecologySubRecordService } from '../../Services/gynecology-sub-record.service';
import { CommonModule } from '@angular/common';
import { ConsultationListComponent } from '../consultation-list/consultation-list.component';
import { ConsultationDetailComponent } from "../consultation-detail/consultation-detail.component";
import { ConsultationFormComponent } from '../consultation-form/consultation-form.component';

@Component({
  selector: 'app-gynecology-sub-record-detail',
  standalone: true,
  imports: [CommonModule, ConsultationListComponent, ConsultationDetailComponent, ConsultationFormComponent],
  templateUrl: './gynecology-sub-record-detail.component.html',
  styleUrl: './gynecology-sub-record-detail.component.css'
})
export class GynecologySubRecordDetailComponent implements OnInit {
  @Input() subRecordId!: number;
  gynecologySubRecord: GynecologySubRecord | null = null;
  error: string | null = null;
  selectedConsultationId = signal<number | null>(null);
  selectedEditConsultationId = signal<number | null>(null); // New signal for editing
  showCreateConsultationForm = signal<boolean>(false); // New signal for creating

  constructor(private gynecologySubRecordService: GynecologySubRecordService) {}

  ngOnInit() {
    if (this.subRecordId) {
      this.gynecologySubRecordService.getGynecologySubRecord(this.subRecordId)
        .pipe(
          catchError(err => {
            this.error = 'Failed to load gynecology sub-record: ' + err.message;
            return of(null);
          })
        )
        .subscribe(record => {
          this.gynecologySubRecord = record;
          console.log('Fetched Gynecology Sub-Record:', record);
        });
    } else {
      this.error = 'No sub-record ID provided';
    }
  }

  setSelectedConsultationId(id: number | null) {
    this.selectedConsultationId.set(id);
    if (id !== null) {
      this.selectedEditConsultationId.set(null); // Hide edit form when showing details
      this.showCreateConsultationForm.set(false); // Hide create form when showing details
    }
  }

  setSelectedEditConsultationId(id: number | null) {
    this.selectedEditConsultationId.set(id);
    if (id !== null) {
      this.selectedConsultationId.set(null); // Hide details when editing
      this.showCreateConsultationForm.set(false); // Hide create form when editing
    }
  }

  setShowCreateConsultationForm(show: boolean) {
    this.showCreateConsultationForm.set(show);
    if (show) {
      this.selectedConsultationId.set(null); // Hide details when creating
      this.selectedEditConsultationId.set(null); // Hide edit form when creating
    }
  }

  onCreateConsultation() {
    this.setShowCreateConsultationForm(true);
  }

  onCreateConsultationFormSubmitted() {
    this.setShowCreateConsultationForm(false);
    // Optionally reload the gynecology sub-record to get updated consultations
    if (this.subRecordId) {
      this.ngOnInit();
    }
  }
}
