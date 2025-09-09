import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmailAttachment } from '../Models/EmailAttachment';
import { EmailMessage } from '../Models/EmailMessage';
import { ExtractionResponse } from '../Models/ExtractionResponse';

@Injectable({
  providedIn: 'root'
})
export class GmailService {

  private apiUrl = 'http://localhost:8090'; // Your Spring Boot API URL
  private fastApiUrl = 'http://localhost:8000/pdf';
  constructor(private http: HttpClient) { }

  // Used in emails.component.ts - loadEmails() method to fetch all emails from Gmail
  getEmails(): Observable<EmailMessage[]> {
    return this.http.get<EmailMessage[]>(`${this.apiUrl}/gmail/emails`, {
      withCredentials: true
    });
  }

  getAttachment(attachments: EmailAttachment[]): Observable<EmailAttachment[]> {
    return this.http.post<EmailAttachment[]>(`${this.apiUrl}/gmail/aa/attachments`, attachments, {
      withCredentials: true
    });
  }
 processAttachment1(attachments: EmailAttachment[]): Observable<ExtractionResponse> {
        return this.http.post<ExtractionResponse>(`${this.apiUrl}/gmail/process-attachment`, attachments, {
      withCredentials: true
    });
    }
}
