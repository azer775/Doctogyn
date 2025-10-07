import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { TokenService } from '../../Services/token.service';
import { OAuthService } from '../../Services/oauth.service';
import { Cabinet } from '../../Models/Cabinet';
import { CrewTabComponent } from '../crew-tab/crew-tab.component';
import { CabinetFormComponent } from '../cabinet-form/cabinet-form.component';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, MatDialogModule, CrewTabComponent, CabinetFormComponent],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit {
  cabinet: Cabinet | null = null;
  isGoogleAuthenticated = false;
  userProfile: any = null;

  constructor(
    private tokenService: TokenService,
    private dialog: MatDialog,
    private oauthService: OAuthService
  ) {}

  ngOnInit(): void {
    this.loadCabinet();
    this.checkGoogleAuth();
  }

  loadCabinet(): void {
    this.cabinet = this.tokenService.cabinet;
    console.log('Cabinet loaded:', this.cabinet);
  }

  checkGoogleAuth(): void {
    this.oauthService.checkAuthStatus().subscribe({
      next: (isAuthenticated) => {
        this.isGoogleAuthenticated = isAuthenticated;
        if (isAuthenticated) {
          this.loadUserProfile();
        }
      },
      error: (error) => {
        console.error('Error checking Google auth status:', error);
        this.isGoogleAuthenticated = false;
      }
    });
  }

  loadUserProfile(): void {
    this.oauthService.getUserProfile().subscribe({
      next: (profile) => {
        this.userProfile = profile;
        console.log('User profile loaded:', profile);
      },
      error: (error) => {
        console.error('Error loading user profile:', error);
      }
    });
  }

  authenticateWithGoogle(): void {
    this.oauthService.initiateGoogleLogin();
  }

  openCabinetForm(): void {
    if (this.cabinet) {
      const dialogRef = this.dialog.open(CabinetFormComponent, {
        data: { cabinet: this.cabinet },
        width: '700px',
        maxWidth: '90vw',
        maxHeight: '90vh',
        autoFocus: true,
        panelClass: 'custom-dialog-container',
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result) {
          // Reload cabinet info after update
          // Note: Since cabinet is in JWT token, user may need to re-login to see changes
          alert('Cabinet updated! Please log out and log in again to see the changes.');
        }
      });
    }
  }
}
