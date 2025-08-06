import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Echographie } from '../Models/Echographie';

@Injectable({
  providedIn: 'root'
})
export class EchographieService {

private apiUrl = 'http://localhost:8080/echographies';

  constructor(private http: HttpClient) {}

  createEchographie(dto: Echographie): Observable<Echographie> {
    return this.http.post<Echographie>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getEchographie(id: number): Observable<Echographie> {
    return this.http.get<Echographie>(`${this.apiUrl}/byid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllEchographies(): Observable<Echographie[]> {
    return this.http.get<Echographie[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateEchographie(id: number, dto: Echographie): Observable<Echographie> {
    return this.http.put<Echographie>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteEchographie(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
