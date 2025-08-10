import { Component, Input, OnInit, signal } from '@angular/core';
import { catchError, of } from 'rxjs';
import { ObstetricsRecord } from '../../Models/ObstetricsRecord';
import { ObstetricsRecordService } from '../../Services/obstetrics-record.service';
import { CommonModule } from '@angular/common';
import { ConsultationListComponent } from '../consultation-list/consultation-list.component';
import { ConsultationDetailComponent } from "../consultation-detail/consultation-detail.component";

@Component({
  selector: 'app-obstetrics-record-detail',
  standalone: true,
  imports: [CommonModule, ConsultationListComponent, ConsultationDetailComponent],
  templateUrl: './obstetrics-record-detail.component.html',
  styleUrl: './obstetrics-record-detail.component.css'
})
export class ObstetricsRecordDetailComponent  implements OnInit {
  @Input() subRecordId!: number;
  obstetricsRecord: ObstetricsRecord | null = null;
  error: string | null = null;
  selectedConsultationId = signal<number | null>(null);

  constructor(private obstetricsRecordService: ObstetricsRecordService) {}

  ngOnInit() {
    if (this.subRecordId) {
      this.obstetricsRecordService.getObstetricsRecord(this.subRecordId)
        .pipe(
          catchError(err => {
            this.error = 'Failed to load obstetrics record: ' + err.message;
            return of(null);
          })
        )
        .subscribe(record => {
          this.obstetricsRecord = record;
        });
    } else {
      this.error = 'No sub-record ID provided';
    }
  }

  setSelectedConsultationId(id: number | null) {
    this.selectedConsultationId.set(id);
  }
}
