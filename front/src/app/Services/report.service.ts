import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private apiUrl = 'http://localhost:8080/report'; // Updated base URL

  constructor(private http: HttpClient) {}

  generatePdf(htmlContent: string): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post(`${this.apiUrl}/generate`, JSON.stringify(htmlContent), {
      headers: headers,
      responseType: 'blob' // Important: tells Angular to expect binary data
    });
  }
}
