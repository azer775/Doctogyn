import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Serology } from '../Models/Serology';

@Injectable({
  providedIn: 'root'
})
export class SerologyService {
  private apiUrl = 'http://localhost:8090/serologies'; // Updated base URL

  constructor(private http: HttpClient) {}

  createSerology(dto: Serology): Observable<Serology> {
    return this.http.post<Serology>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getSerology(id: number): Observable<Serology> {
    return this.http.get<Serology>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllSerologies(): Observable<Serology[]> {
    return this.http.get<Serology[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateSerology(id: number, dto: Serology): Observable<Serology> {
    return this.http.put<Serology>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteSerology(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getSerologiesByConsultationId(consultationId: number): Observable<Serology[]> {
    return this.http.get<Serology[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
