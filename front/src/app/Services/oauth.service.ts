import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OAuthService {

  
  private apiUrl = 'http://localhost:8090'; 

  constructor(private http: HttpClient) { }
  
  // Used in login.component.ts - loginWithGoogle() method to initiate Google OAuth login
  initiateGoogleLogin(): void {
    // This triggers the OAuth flow through backend redirect
    window.location.href = `${this.apiUrl}/oauth2/authorization/google`;
  }

  // Used in login.component.ts - ngOnInit() method and emails.component.ts - ngOnInit() method to check user authentication status
  checkAuthStatus(): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/gmail/user/authenticated`, {
      withCredentials: true // Important for session cookies
    });
  }
  
  

  // Used in login.component.ts - Details() method to fetch user profile information
  getUserProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/gmail/user/profile`, {
      withCredentials: true
    });
  }


}
