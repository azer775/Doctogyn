import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Biology } from '../Models/Biology';

@Injectable({
  providedIn: 'root'
})
export class BiologyService {
  private apiUrl = 'http://localhost:8090/biologies'; // Updated base URL

  constructor(private http: HttpClient) {}

  createBiology(dto: Biology): Observable<Biology> {
    return this.http.post<Biology>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getBiology(id: number): Observable<Biology> {
    return this.http.get<Biology>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllBiologies(): Observable<Biology[]> {
    return this.http.get<Biology[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateBiology(id: number, dto: Biology): Observable<Biology> {
    return this.http.put<Biology>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteBiology(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getBiologiesByConsultationId(consultationId: number): Observable<Biology[]> {
    return this.http.get<Biology[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
