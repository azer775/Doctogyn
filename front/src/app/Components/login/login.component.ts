import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthentificationService } from '../../Services/authentification.service';
import { TokenService } from '../../Services/token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthentificationService,
    private router: Router,
    private tokenService: TokenService
  ) {
    // Initialize login form
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  /**
   * Handle form submission
   */
  onSubmit(): void {
    // Clear previous error message
    this.errorMessage = '';

    // Check if form is valid
    if (this.loginForm.invalid) {
      this.markFormGroupTouched(this.loginForm);
      return;
    }

    // Set loading state
    this.isLoading = true;

    // Transform form data to match backend DTO (password -> pwd)
    const authRequest = {
      email: this.loginForm.value.email,
      pwd: this.loginForm.value.password
    };

    // Call authentication service
    this.authService.login(authRequest).subscribe({
      next: (response) => {
        console.log('Login successful', response);
        
        // Store token in localStorage using TokenService
        if (response.token) {
          this.tokenService.token = response.token;
        }
        
        // Show success message if provided
        if (response.message) {
          console.log('Server message:', response.message);
        }
        console.log("User roles:", this.tokenService.userRoles);
        console.log("User cabinet:", this.tokenService.cabinet);
        // Navigate to home or dashboard
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Login error:', error);
        
        // Handle different error scenarios
        if (error.status === 401) {
          this.errorMessage = 'Invalid email or password';
        } else if (error.status === 403) {
          this.errorMessage = 'Access forbidden';
        } else if (error.status === 0) {
          this.errorMessage = 'Cannot connect to server. Please try again later.';
        } else {
          this.errorMessage = error.error?.message || 'An error occurred during login';
        }
      },
      complete: () => {
        this.isLoading = false;
      }
    });
  }

  /**
   * Mark all form controls as touched to show validation errors
   */
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.keys(formGroup.controls).forEach(key => {
      const control = formGroup.get(key);
      control?.markAsTouched();
    });
  }

  /**
   * Check if a form control has an error
   */
  hasError(controlName: string, errorName: string): boolean {
    const control = this.loginForm.get(controlName);
    return !!(control?.hasError(errorName) && control?.touched);
  }

  /**
   * Get error message for a form control
   */
  getErrorMessage(controlName: string): string {
    const control = this.loginForm.get(controlName);
    
    if (control?.hasError('required')) {
      return `${controlName.charAt(0).toUpperCase() + controlName.slice(1)} is required`;
    }
    
    if (control?.hasError('email')) {
      return 'Please enter a valid email address';
    }
    
    if (control?.hasError('minlength')) {
      const minLength = control.getError('minlength').requiredLength;
      return `Password must be at least ${minLength} characters`;
    }
    
    return '';
  }
}
