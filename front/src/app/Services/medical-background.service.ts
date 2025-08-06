import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { MedicalBackground } from '../Models/MedicalBackground';

@Injectable({
  providedIn: 'root'
})
export class MedicalBackgroundService {

private apiUrl = 'http://localhost:8080/medical-backgrounds';

  constructor(private http: HttpClient) {}
  addMBList(mbList: MedicalBackground[]): Observable<MedicalBackground[]> {
    return this.http.post<MedicalBackground[]>(`${this.apiUrl}/addlist`, mbList);
  }

  createMedicalBackground(dto: MedicalBackground): Observable<MedicalBackground> {
    return this.http.post<MedicalBackground>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getMedicalBackground(id: number): Observable<MedicalBackground> {
    return this.http.get<MedicalBackground>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllMedicalBackgrounds(): Observable<MedicalBackground[]> {
    return this.http.get<MedicalBackground[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  updateMedicalBackground(id: number, dto: MedicalBackground): Observable<MedicalBackground> {
    return this.http.put<MedicalBackground>(`${this.apiUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteMedicalBackground(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
