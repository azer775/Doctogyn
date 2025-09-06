import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Radiology } from '../Models/Radiology';

@Injectable({
  providedIn: 'root'
})
export class RadiologyService {
  private apiUrl = 'http://localhost:8090/radiologies'; // Updated base URL

  constructor(private http: HttpClient) {}

  createRadiology(dto: Radiology): Observable<Radiology> {
    return this.http.post<Radiology>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getRadiology(id: number): Observable<Radiology> {
    return this.http.get<Radiology>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllRadiologies(): Observable<Radiology[]> {
    return this.http.get<Radiology[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  updateRadiology(id: number, dto: Radiology): Observable<Radiology> {
    return this.http.put<Radiology>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteRadiology(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getRadiologiesByConsultationId(consultationId: number): Observable<Radiology[]> {
    return this.http.get<Radiology[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
