import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { BloodGroup } from '../Models/BloodGroup';

@Injectable({
  providedIn: 'root'
})
export class BloodGroupService {
  private apiUrl = 'http://localhost:8090/blood-groups'; // Updated base URL

  constructor(private http: HttpClient) {}

  createBloodGroup(dto: BloodGroup): Observable<BloodGroup> {
    return this.http.post<BloodGroup>(`${this.apiUrl}/add`, dto).pipe(
      catchError(this.handleError)
    );
  }

  getBloodGroup(id: number): Observable<BloodGroup> {
    return this.http.get<BloodGroup>(`${this.apiUrl}/getbyid/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAllBloodGroups(): Observable<BloodGroup[]> {
    return this.http.get<BloodGroup[]>(`${this.apiUrl}/all`).pipe(
      catchError(this.handleError)
    );
  }

  updateBloodGroup(id: number, dto: BloodGroup): Observable<BloodGroup> {
    return this.http.put<BloodGroup>(`${this.apiUrl}/update/${id}`, dto).pipe(
      catchError(this.handleError)
    );
  }

  deleteBloodGroup(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getBloodGroupsByConsultationId(consultationId: number): Observable<BloodGroup[]> {
    return this.http.get<BloodGroup[]>(`${this.apiUrl}/consultation/${consultationId}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('An error occurred:', error.message);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }
}
