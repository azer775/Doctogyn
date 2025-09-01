import { Component, OnInit } from '@angular/core';
import { OAuthService } from '../../Services/oauth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GmailService } from '../../Services/gmail.service';
import { EmailsComponent } from '../emails/emails.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login-oauth',
  standalone: true,
  imports: [CommonModule, MatButtonModule,MatDialogModule],
  templateUrl: './login-oauth.component.html',
  styleUrl: './login-oauth.component.css'
})
export class LoginOauthComponent implements OnInit {
  
  isLoggedIn = false;
  isLoading = false;
  errorMessage: string | null = null;
  selectedFile: File | null = null;
  uploadProgress: number | null = null;

  constructor(
    private authService: OAuthService,
    private router: Router,
    private dialog: MatDialog,
    private gmailService: GmailService,
  ) {}
  ngOnInit(): void {
    this.authService.checkAuthStatus().subscribe(isAuthenticated => {
      if(isAuthenticated) {
        this.isLoggedIn = true;
      }
    })
  }

  loginWithGoogle(): void {
    this.isLoading = true;
    this.errorMessage = null;
    
    try {
      this.authService.initiateGoogleLogin();
    } catch (error) {
      this.errorMessage = 'Failed to initiate login';
      this.isLoading = false;
      console.error('Login error:', error);
    }
  }
  Details(): void{
    this.authService.getUserProfile().subscribe(r=>console.log(r))
  }
  openEmailsDialog(): void {
    const dialogRef = this.dialog.open(EmailsComponent, {
      width: '98vw',
      height: '95vh',
      maxWidth: '100vw',
      maxHeight: '100vh',
      panelClass: ['emails-dialog', 'fullscreen-dialog'],
      disableClose: false,
      autoFocus: false,
      restoreFocus: false,
      hasBackdrop: true,
      backdropClass: 'emails-dialog-backdrop',
      position: {
        top: '2.5vh',
        left: '1vw'
      },
      data: { source: 'login' } // Pass context data
    });

    // Handle dialog events
    dialogRef.afterOpened().subscribe(() => {
      console.log('Emails dialog opened');
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Emails dialog closed', result);
    });
  }
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

}
