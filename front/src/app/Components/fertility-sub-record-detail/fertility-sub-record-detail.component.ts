import { Component, Input, OnInit } from '@angular/core';
import { FertilityfubrecordService } from '../../Services/fertilityfubrecord.service';
import { catchError, of } from 'rxjs';
import { FertilitySubRecord } from '../../Models/FertilitySubRecord';
import { CommonModule } from '@angular/common';
import { ConsultationListComponent } from '../consultation-list/consultation-list.component';

@Component({
  selector: 'app-fertility-sub-record-detail',
  standalone: true,
  imports: [CommonModule,ConsultationListComponent],
  templateUrl: './fertility-sub-record-detail.component.html',
  styleUrl: './fertility-sub-record-detail.component.css'
})
export class FertilitySubRecordDetailComponent implements OnInit {
  @Input() subRecordId!: number;
  fertilitySubRecord: FertilitySubRecord | null = null;
  error: string | null = null;

  constructor(private fertilitySubRecordService: FertilityfubrecordService) {}

  ngOnInit() {
    if (this.subRecordId) {
      this.fertilitySubRecordService.getFertilitySubRecord(this.subRecordId)
        .pipe(
          catchError(err => {
            this.error = 'Failed to load fertility sub-record: ' + err.message;
            return of(null);
          })
        )
        .subscribe(record => {
          this.fertilitySubRecord = record;
        });
    } else {
      this.error = 'No sub-record ID provided';
    }
  }

}
