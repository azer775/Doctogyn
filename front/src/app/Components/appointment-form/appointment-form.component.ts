import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AppointmentService } from '../../Services/appointment.service';
import { MedicalRecordService } from '../../Services/medical-record.service';
import { MedicalRecord } from '../../Models/MedicalRecord';
import { Appointment } from '../../Models/Appointment';
import { ConsultationType } from '../../Models/enums';
import { TokenService } from '../../Services/token.service';

@Component({
  selector: 'app-appointment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './appointment-form.component.html',
  styleUrl: './appointment-form.component.css'
})
export class AppointmentFormComponent implements OnInit {
  appointmentForm: FormGroup;
  medicalRecords: MedicalRecord[] = [];
  consultationTypes = Object.values(ConsultationType);
  isEditMode: boolean = false;
  appointmentId?: number;

  constructor(
    private fb: FormBuilder,
    private appointmentService: AppointmentService,
    private medicalRecordService: MedicalRecordService,
    private tokenService: TokenService,
    private dialogRef: MatDialogRef<AppointmentFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { appointment?: Appointment }
  ) {
    this.appointmentForm = this.fb.group({
      date: ['', [Validators.required]],
      reason: ['', [Validators.required]],
      consultationType: ['', [Validators.required]],
      medicalRecordId: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.loadMedicalRecords();
    
    // If appointment data is passed, populate the form (edit mode)
    if (this.data?.appointment) {
      this.isEditMode = true;
      this.appointmentId = this.data.appointment.id;
      this.populateForm(this.data.appointment);
    }
  }

  loadMedicalRecords(): void {
    this.medicalRecordService.getCabinetMedicalRecords().subscribe({
      next: (records) => {
        this.medicalRecords = records;
      },
      error: (error) => {
        console.error('Error loading medical records', error);
        alert('Error loading medical records. Please try again.');
      }
    });
  }

  populateForm(appointment: Appointment): void {
    // Format date for datetime-local input (YYYY-MM-DDTHH:mm)
    const date = new Date(appointment.date);
    const formattedDate = date.toISOString().slice(0, 16);
    
    this.appointmentForm.patchValue({
      date: formattedDate,
      reason: appointment.reason,
      consultationType: appointment.consultationType,
      medicalRecordId: appointment.medicalRecord?.id || ''
    });
  }

  onSubmit(): void {
    if (this.appointmentForm.valid) {
      const selectedMedicalRecord = this.medicalRecords.find(
        mr => mr.id === +this.appointmentForm.value.medicalRecordId
      );

      const appointmentData: Appointment = {
        id: this.isEditMode ? this.appointmentId : undefined,
        date: new Date(this.appointmentForm.value.date),
        reason: this.appointmentForm.value.reason,
        consultationType: this.appointmentForm.value.consultationType,
        medicalRecord: selectedMedicalRecord!,
        cabinetId: this.tokenService.cabinet?.id || 0
      };

      this.appointmentService.add(appointmentData).subscribe({
        next: (response) => {
          console.log('Appointment saved successfully', response);
          alert(this.isEditMode ? 'Appointment updated successfully!' : 'Appointment created successfully!');
          this.dialogRef.close(response);
        },
        error: (error) => {
          console.error('Error saving appointment', error);
          alert('Error saving appointment. Please try again.');
        }
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
