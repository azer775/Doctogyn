import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Cabinet } from '../../Models/Cabinet';
import { CabinetService } from '../../Services/cabinet.service';

@Component({
  selector: 'app-cabinet-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cabinet-form.component.html',
  styleUrl: './cabinet-form.component.css'
})
export class CabinetFormComponent implements OnInit {
  cabinetForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cabinetService: CabinetService,
    private dialogRef: MatDialogRef<CabinetFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { cabinet: Cabinet }
  ) {
    this.cabinetForm = this.fb.group({
      adress: ['', [Validators.required]],
      tel: ['', [Validators.required]],
      fertilityRate: [0, [Validators.required, Validators.min(0)]],
      gynecologyRate: [0, [Validators.required, Validators.min(0)]],
      obstetricsRate: [0, [Validators.required, Validators.min(0)]],
      echographyRate: [0, [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    if (this.data?.cabinet) {
      this.cabinetForm.patchValue({
        adress: this.data.cabinet.adress,
        tel: this.data.cabinet.tel,
        fertilityRate: this.data.cabinet.fertilityRate,
        gynecologyRate: this.data.cabinet.gynecologyRate,
        obstetricsRate: this.data.cabinet.obstetricsRate,
        echographyRate: this.data.cabinet.echographyRate
      });
    }
  }

  onSubmit(): void {
    if (this.cabinetForm.valid) {
      const updatedCabinet: Cabinet = {
        id: this.data.cabinet.id,
        adress: this.cabinetForm.value.adress,
        tel: this.cabinetForm.value.tel,
        fertilityRate: this.cabinetForm.value.fertilityRate,
        gynecologyRate: this.cabinetForm.value.gynecologyRate,
        obstetricsRate: this.cabinetForm.value.obstetricsRate,
        echographyRate: this.cabinetForm.value.echographyRate
      };

      this.cabinetService.updateCabinet(updatedCabinet).subscribe({
        next: (response) => {
          console.log('Cabinet updated successfully', response);
          alert('Cabinet updated successfully!');
          this.dialogRef.close(response);
        },
        error: (error) => {
          console.error('Error updating cabinet', error);
          alert('Error updating cabinet. Please try again.');
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
