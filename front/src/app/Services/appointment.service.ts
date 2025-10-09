import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Appointment } from '../Models/Appointment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private apiUrl = 'http://localhost:8080/appointment';

  constructor(private http: HttpClient) { }
  add(appointment: Appointment): Observable<Appointment> {
    return this.http.post<Appointment>(`${this.apiUrl}/add`, appointment);
  }
  getByCabinet(id: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/getByCabinet/${id}`);
  }
}
