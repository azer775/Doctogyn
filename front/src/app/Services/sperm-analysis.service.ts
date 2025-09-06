import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { SpermAnalysis } from '../Models/SpermAnalysis';

@Injectable({
  providedIn: 'root'
})
export class SpermAnalysisService {
  private apiUrl = 'http://localhost:8090/sperm-analyses'; // Updated base URL

  constructor(private http: HttpClient) {}

  createSpermAnalysis(dto: SpermAnalysis): Observable<SpermAnalysis> {
    return this.http.post<SpermAnalysis>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getSpermAnalysis(id: number): Observable<SpermAnalysis> {
    return this.http.get<SpermAnalysis>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllSpermAnalyses(): Observable<SpermAnalysis[]> {
    return this.http.get<SpermAnalysis[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateSpermAnalysis(id: number, dto: SpermAnalysis): Observable<SpermAnalysis> {
    return this.http.put<SpermAnalysis>(`${this.apiUrl}/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteSpermAnalysis(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getSpermAnalysesByConsultationId(consultationId: number): Observable<SpermAnalysis[]> {
    return this.http.get<SpermAnalysis[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
