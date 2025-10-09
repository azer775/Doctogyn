import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppointmentService } from '../../Services/appointment.service';
import { Appointment } from '../../Models/Appointment';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ConsultationType } from '../../Models/enums';
import { TokenService } from '../../Services/token.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AppointmentFormComponent } from '../appointment-form/appointment-form.component';

interface TimeSlot {
  hour: number;
  displayHour: string;
  appointments: Appointment[];
}

interface WeekDay {
  date: Date;
  dayName: string;
  dayNumber: number;
  isToday: boolean;
  timeSlots: TimeSlot[];
}

@Component({
  selector: 'app-scheduler2',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule
  ],
  templateUrl: './scheduler2.component.html',
  styleUrl: './scheduler2.component.css'
})
export class Scheduler2Component implements OnInit {
  currentDate: Date = new Date();
  currentWeekStart: Date = new Date();
  weekDays: WeekDay[] = [];
  appointments: Appointment[] = [];
  viewMode: 'week' | 'day' = 'week';
  selectedDate: Date = new Date();
  cabinetId: number = 0; // Will be set from token service
  
  hours: number[] = Array.from({ length: 12 }, (_, i) => i + 8); // 8 AM to 7 PM

  constructor(
    private appointmentService: AppointmentService,
    private router: Router,
    private tokenService: TokenService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    // Get cabinet ID from token service
    const cabinet = this.tokenService.cabinet;
    if (cabinet && cabinet.id) {
      this.cabinetId = cabinet.id;
    } else {
      console.error('Cabinet ID not found in token');
      // Optionally redirect to login or show error
    }
    
    this.setWeekStart(this.currentDate);
    this.loadAppointments();
  }

  setWeekStart(date: Date): void {
    const day = date.getDay();
    const diff = date.getDate() - day;
    this.currentWeekStart = new Date(date.setDate(diff));
    this.selectedDate = new Date(this.currentWeekStart);
  }

  loadAppointments(): void {
    if (!this.cabinetId) {
      console.error('Cannot load appointments: Cabinet ID is not set');
      this.generateSchedule();
      return;
    }

    this.appointmentService.getByCabinet(this.cabinetId).subscribe({
      next: (data) => {
        this.appointments = data.map(appointment => ({
          ...appointment,
          date: new Date(appointment.date)
        }));
        this.generateSchedule();
        console.log('Loaded appointments:', this.appointments);
      },
      error: (error) => {
        console.error('Error loading appointments:', error);
        this.appointments = [];
        this.generateSchedule();
      }
    });
  }

  generateSchedule(): void {
    this.weekDays = [];
    
    if (this.viewMode === 'week') {
      // Generate 7 days for week view
      for (let i = 0; i < 7; i++) {
        const date = new Date(this.currentWeekStart);
        date.setDate(date.getDate() + i);
        this.weekDays.push(this.createWeekDay(date));
      }
    } else {
      // Generate single day for day view
      this.weekDays.push(this.createWeekDay(this.selectedDate));
    }
  }

  createWeekDay(date: Date): WeekDay {
    const timeSlots: TimeSlot[] = this.hours.map(hour => ({
      hour,
      displayHour: this.formatHour(hour),
      appointments: this.getAppointmentsForTimeSlot(date, hour)
    }));

    return {
      date,
      dayName: date.toLocaleDateString('en-US', { weekday: 'short' }),
      dayNumber: date.getDate(),
      isToday: this.isToday(date),
      timeSlots
    };
  }

  getAppointmentsForTimeSlot(date: Date, hour: number): Appointment[] {
    return this.appointments.filter(appointment => {
      const appointmentDate = new Date(appointment.date);
      return this.isSameDay(appointmentDate, date) && 
             appointmentDate.getHours() === hour;
    });
  }

  formatHour(hour: number): string {
    const period = hour >= 12 ? 'PM' : 'AM';
    const displayHour = hour > 12 ? hour - 12 : hour === 0 ? 12 : hour;
    return `${displayHour}:00 ${period}`;
  }

  isToday(date: Date): boolean {
    return this.isSameDay(date, new Date());
  }

  isSameDay(date1: Date, date2: Date): boolean {
    return date1.getDate() === date2.getDate() &&
           date1.getMonth() === date2.getMonth() &&
           date1.getFullYear() === date2.getFullYear();
  }

  previousWeek(): void {
    if (this.viewMode === 'week') {
      this.currentWeekStart.setDate(this.currentWeekStart.getDate() - 7);
    } else {
      this.selectedDate.setDate(this.selectedDate.getDate() - 1);
    }
    this.generateSchedule();
  }

  nextWeek(): void {
    if (this.viewMode === 'week') {
      this.currentWeekStart.setDate(this.currentWeekStart.getDate() + 7);
    } else {
      this.selectedDate.setDate(this.selectedDate.getDate() + 1);
    }
    this.generateSchedule();
  }

  goToToday(): void {
    this.currentDate = new Date();
    this.setWeekStart(new Date());
    this.selectedDate = new Date();
    this.generateSchedule();
  }

  switchView(mode: 'week' | 'day'): void {
    this.viewMode = mode;
    this.generateSchedule();
  }

  openAddAppointmentDialog(): void {
    const dialogRef = this.dialog.open(AppointmentFormComponent, {
      width: '600px',
      data: { appointment: null }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Appointment was created, reload appointments
        console.log('New appointment created:', result);
        this.loadAppointments();
      }
    });
  }

  viewAppointment(appointment: Appointment): void {
    const dialogRef = this.dialog.open(AppointmentFormComponent, {
      width: '600px',
      data: { appointment: appointment }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Appointment was updated, reload appointments
        console.log('Appointment updated:', result);
        this.loadAppointments();
      }
    });
  }

  getConsultationTypeColor(consultationType: string): string {
    switch (consultationType) {
      case ConsultationType.GYNECOLOGY:
      case 'GYNECOLOGY':
      case 'Gynecology':
        return '#e91e63';
      case ConsultationType.FERTILITY:
      case 'FERTILITY':
      case 'Fertility':
        return '#9c27b0';
      case ConsultationType.OBSTETRICS:
      case 'OBSTETRICS':
      case 'Obstetrics':
        return '#2196f3';
      default:
        return '#757575';
    }
  }

  getCurrentPeriod(): string {
    if (this.viewMode === 'week') {
      const endDate = new Date(this.currentWeekStart);
      endDate.setDate(endDate.getDate() + 6);
      return `${this.currentWeekStart.toLocaleDateString('en-US', { month: 'short', day: 'numeric' })} - ${endDate.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' })}`;
    } else {
      return this.selectedDate.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric', year: 'numeric' });
    }
  }
}
