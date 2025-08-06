import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Consultation } from '../Models/Consultation';

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {
  private apiUrl = 'http://localhost:8080/consultations';

  constructor(private http: HttpClient) {}

  createConsultation(dto: Consultation): Observable<Consultation> {
    return this.http.post<Consultation>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getConsultation(id: number): Observable<Consultation> {
    return this.http.get<Consultation>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllConsultations(): Observable<Consultation[]> {
    return this.http.get<Consultation[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateConsultation(id: number, dto: Consultation): Observable<Consultation> {
    return this.http.put<Consultation>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteConsultation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
