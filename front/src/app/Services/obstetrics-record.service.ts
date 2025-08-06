import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { ObstetricsRecord } from '../Models/ObstetricsRecord';

@Injectable({
  providedIn: 'root'
})
export class ObstetricsRecordService {

  private apiUrl = 'http://localhost:8080/obstetrics-records';

  constructor(private http: HttpClient) {}

  createObstetricsRecord(dto: ObstetricsRecord): Observable<ObstetricsRecord> {
    return this.http.post<ObstetricsRecord>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getObstetricsRecord(id: number): Observable<ObstetricsRecord> {
    return this.http.get<ObstetricsRecord>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllObstetricsRecords(): Observable<ObstetricsRecord[]> {
    return this.http.get<ObstetricsRecord[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateObstetricsRecord(id: number, dto: ObstetricsRecord): Observable<ObstetricsRecord> {
    return this.http.put<ObstetricsRecord>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteObstetricsRecord(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
