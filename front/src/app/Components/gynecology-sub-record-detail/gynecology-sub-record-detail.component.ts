import { Component, Input, OnInit } from '@angular/core';
import { catchError, of } from 'rxjs';
import { GynecologySubRecord } from '../../Models/GynecologySubRecord';
import { GynecologySubRecordService } from '../../Services/gynecology-sub-record.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-gynecology-sub-record-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gynecology-sub-record-detail.component.html',
  styleUrl: './gynecology-sub-record-detail.component.css'
})
export class GynecologySubRecordDetailComponent implements OnInit {
  @Input() subRecordId!: number;
  gynecologySubRecord: GynecologySubRecord | null = null;
  error: string | null = null;

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
        });
    } else {
      this.error = 'No sub-record ID provided';
    }
  }

}
