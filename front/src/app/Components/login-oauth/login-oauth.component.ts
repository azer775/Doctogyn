import { Component, OnInit } from '@angular/core';
import { OAuthService } from '../../Services/oauth.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { GmailService } from '../../Services/gmail.service';
import { EmailsComponent } from '../emails/emails.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { AnalysesListComponent } from '../analyses-list/analyses-list.component';
import { ExtractionResponse } from '../../Models/ExtractionResponse';
import { Document } from '../../Models/Document';
import { Bacteriology } from '../../Models/Bacteriology';
import { Biology } from '../../Models/Biology';
import { BloodGroup } from '../../Models/BloodGroup';
import { Radiology } from '../../Models/Radiology';
import { Serology } from '../../Models/Serology';
import { SpermAnalysis } from '../../Models/SpermAnalysis';

@Component({
  selector: 'app-login-oauth',
  standalone: true,
  imports: [CommonModule, MatButtonModule,MatDialogModule,AnalysesListComponent],
  templateUrl: './login-oauth.component.html',
  styleUrl: './login-oauth.component.css'
})
export class LoginOauthComponent implements OnInit {
  
  isLoggedIn = false;
  isLoading = false;
  errorMessage: string | null = null;
  selectedFile: File | null = null;
  uploadProgress: number | null = null;
  
  // Data for analyses list component
  collectedAnalysesData: ExtractionResponse | null = null;

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
      
      // Handle collected analyses data
      if (result && result.type === 'analysesCollected') {
        this.handleCollectedAnalyses(result.data);
      }
    });
  }

  /**
   * Handles collected analyses data from the emails dialog
   * Transforms the data into ExtractionResponse format for AnalysesListComponent
   * @param collectedData - Raw collected analyses data
   */
  handleCollectedAnalyses(collectedData: {
    biologies: Biology[],
    bacteriologies: Bacteriology[],
    bloodGroups: BloodGroup[],
    radiologies: Radiology[],
    serologies: Serology[],
    spermAnalyses: SpermAnalysis[]
  }): void {
    console.log('Processing collected analyses data:', collectedData);
    
    // Create a Document object with all the collected data
    const document = new Document();
    document.biologies = collectedData.biologies || [];
    document.bacteriologies = collectedData.bacteriologies || [];
    document.bloodgroups = collectedData.bloodGroups || []; // Note: using 'bloodgroups' to match Document model
    document.radiologies = collectedData.radiologies || [];
    document.serologies = collectedData.serologies || [];
    document.spermAnalyses = collectedData.spermAnalyses || [];

    // Create ExtractionResponse with the document
    const extractionResponse = new ExtractionResponse();
    extractionResponse.documents = [document];

    // Set the data for the analyses list component
    this.collectedAnalysesData = extractionResponse;
    
    console.log('Created ExtractionResponse for AnalysesListComponent:', this.collectedAnalysesData);
    
    // Optional: Show success message
    const totalAnalyses = this.getTotalAnalysesCount(collectedData);
    alert(`Successfully collected ${totalAnalyses} analyses. Now displaying in the analyses list below.`);
  }

  /**
   * Counts total number of analyses across all types
   */
  private getTotalAnalysesCount(data: any): number {
    return (data.biologies?.length || 0) +
           (data.bacteriologies?.length || 0) +
           (data.bloodGroups?.length || 0) +
           (data.radiologies?.length || 0) +
           (data.serologies?.length || 0) +
           (data.spermAnalyses?.length || 0);
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }
}
