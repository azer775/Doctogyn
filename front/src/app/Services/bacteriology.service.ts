import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Bacteriology } from '../Models/Bacteriology';

@Injectable({
  providedIn: 'root'
})
export class BacteriologyService {
  private apiUrl = 'http://localhost:8090/bacteriologies'; // Updated base URL

  constructor(private http: HttpClient) {}

  createBacteriology(dto: Bacteriology): Observable<Bacteriology> {
    return this.http.post<Bacteriology>(this.apiUrl, dto).pipe(
      catchError(this.handleError)
    );
  }

  getBacteriology(id: number): Observable<Bacteriology> {
    return this.http.get<Bacteriology>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllBacteriologies(): Observable<Bacteriology[]> {
    return this.http.get<Bacteriology[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  updateBacteriology(id: number, dto: Bacteriology): Observable<Bacteriology> {
    return this.http.put<Bacteriology>(`${this.apiUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteBacteriology(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getBacteriologiesByConsultationId(consultationId: number): Observable<Bacteriology[]> {
    return this.http.get<Bacteriology[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
