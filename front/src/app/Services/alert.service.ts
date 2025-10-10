import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Consultation } from '../Models/Consultation';
import { Observable } from 'rxjs';
import { AlertRequest } from '../Models/AlertRequest';
import { AlertResponse } from '../Models/AlertResponse';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  private apiUrl = 'http://localhost:8080/alert';

  constructor(private http: HttpClient) { }
  getAlerts(consultation: Consultation,id: number): Observable<AlertResponse> {
    return this.http.post<AlertResponse>(`${this.apiUrl}/create/${id}`, consultation );
  }
}
