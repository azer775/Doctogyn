import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { FertilitySubRecord } from '../Models/FertilitySubRecord';

@Injectable({
  providedIn: 'root'
})
export class FertilityfubrecordService {

    private apiUrl = 'http://localhost:8080/fertility-sub-records';

  constructor(private http: HttpClient) {}

  createFertilitySubRecord(dto: FertilitySubRecord): Observable<FertilitySubRecord> {
    return this.http.post<FertilitySubRecord>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getFertilitySubRecord(id: number): Observable<FertilitySubRecord> {
    return this.http.get<FertilitySubRecord>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllFertilitySubRecords(): Observable<FertilitySubRecord[]> {
    return this.http.get<FertilitySubRecord[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateFertilitySubRecord(id: number, dto: FertilitySubRecord): Observable<FertilitySubRecord> {
    return this.http.put<FertilitySubRecord>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteFertilitySubRecord(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
