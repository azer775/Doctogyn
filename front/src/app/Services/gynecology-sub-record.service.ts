import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { GynecologySubRecord } from '../Models/GynecologySubRecord';

@Injectable({
  providedIn: 'root'
})
export class GynecologySubRecordService {
  private apiUrl = 'http://localhost:8080/gynecology-sub-records';

  constructor(private http: HttpClient) {}

  createGynecologySubRecord(dto: GynecologySubRecord): Observable<GynecologySubRecord> {
    return this.http.post<GynecologySubRecord>(this.apiUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  getGynecologySubRecord(id: number): Observable<GynecologySubRecord> {
    return this.http.get<GynecologySubRecord>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllGynecologySubRecords(): Observable<GynecologySubRecord[]> {
    return this.http.get<GynecologySubRecord[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  updateGynecologySubRecord(id: number, dto: GynecologySubRecord): Observable<GynecologySubRecord> {
    return this.http.put<GynecologySubRecord>(`${this.apiUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteGynecologySubRecord(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
