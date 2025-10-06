import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { TokenService } from './token.service';
import { Crew } from '../Models/Crew';

export interface AuthenticationRequest {
  email: string;
  pwd: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface AuthenticationResponse {
  token: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  private apiUrl = 'http://localhost:8020/auth';

  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private router: Router
  ) { }

  /**
   * Login user with email and password
   */
  login(loginRequest: AuthenticationRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/authenticate`, loginRequest)
      .pipe(
        tap(response => {
          // Store the token
          if (response.token) {
            this.tokenService.token = response.token;
          }
        })
      );
  }

  /**
   * Register a new user
   */
  register(registerRequest: RegisterRequest): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(`${this.apiUrl}/register`, registerRequest)
      .pipe(
        tap(response => {
          // Store the token after successful registration
          if (response.token) {
            this.tokenService.token = response.token;
          }
        })
      );
  }
  addCrew(crew: Crew): Observable<Crew> {
    return this.http.post<Crew>(`${this.apiUrl}/addcrew`, crew);
  }
  getAllCrews(): Observable<Crew[]> {
    return this.http.get<Crew[]>(`${this.apiUrl}/getCrewByCabinet`);
  }

  lockUnlockCrew(id: number): Observable<string> {
    return this.http.put(`${this.apiUrl}/lockUnlock/${id}`, null, { responseType: 'text' });
  }

  deleteCrew(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/delete/${id}`, { responseType: 'text' });
  }

  /**
   * Logout user
   */
  logout(): void {
    // Clear token from localStorage
    localStorage.clear();
    // Navigate to login page
    this.router.navigate(['/login']);
  }

  /**
   * Check if user is authenticated
   */
  isAuthenticated(): boolean {
    return this.tokenService.isTokenValid();
  }

  /**
   * Get current user roles
   */
  getUserRoles(): string[] {
    return this.tokenService.userRoles;
  }
}
