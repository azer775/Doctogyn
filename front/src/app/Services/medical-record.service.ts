import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { MedicalRecord } from '../Models/MedicalRecord';
import { FinalResponse } from '../Models/FinalResponse';

@Injectable({
  providedIn: 'root'
})
export class MedicalRecordService {

private apiUrl = 'http://localhost:8080/medical-records';

  constructor(private http: HttpClient) {}

  createMedicalRecord(dto: MedicalRecord): Observable<MedicalRecord> {
    return this.http.post<MedicalRecord>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getMedicalRecord(id: number): Observable<MedicalRecord> {
    return this.http.get<MedicalRecord>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllMedicalRecords(): Observable<MedicalRecord[]> {
    return this.http.get<MedicalRecord[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }
  getCabinetMedicalRecords(): Observable<MedicalRecord[]> {
    return this.http.get<MedicalRecord[]>(`${this.apiUrl}/NamesByCabinet`).pipe(
      catchError(this.handleError)
    );
  }

  updateMedicalRecord(id: number, dto: MedicalRecord): Observable<MedicalRecord> {
    return this.http.put<MedicalRecord>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteMedicalRecord(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getResume(id: number): Observable<FinalResponse> {
    return this.http.get<FinalResponse>(`${this.apiUrl}/getresume/${id}`).pipe(
      catchError(this.handleError)
    );
  }
  getResumewithAbbreviation(id: number, abbreviations: any[]): Observable<FinalResponse> {
    return this.http.post<FinalResponse>(`${this.apiUrl}/getresabb/${id}`, abbreviations).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
