import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { EmailAttachment } from '../../Models/EmailAttachment';
import { EmailMessage } from '../../Models/EmailMessage';
import { GmailService } from '../../Services/gmail.service';
import { OAuthService } from '../../Services/oauth.service';
import { CommonModule, DatePipe, NgFor, NgIf } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';

@Component({
  selector: 'app-emails',
  standalone: true,
  imports: [    NgFor, 
    NgIf, 
    DatePipe,
    PdfViewerModule],
  templateUrl: './emails.component.html',
  styleUrl: './emails.component.css'
})
export class EmailsComponent  {
  // ===== COMPONENT PROPERTIES =====
  
  /** Array to store all email messages fetched from Gmail */
  emails: EmailMessage[] = [];
  
  /** Array to track currently selected email attachments for batch operations */
  selectedAttachments: EmailAttachment[] = [];
  
  /** Reference to the currently active/focused attachment */
  currentSelectedAttachment: EmailAttachment | null = null;
  
  /** URL for displaying a single PDF (backward compatibility) */
  displayedPdfUrl: string | null = null;
  
  /** Array to manage multiple PDF URLs for simultaneous viewing */
  displayedPdfUrls: { attachment: EmailAttachment, url: string }[] = [];
  
  /** Progress indicator for file upload operations (0-100) */
  uploadProgress: number | null = null;

  /**
   * Constructor - Initializes the emails component with required services
   * @param gmailService - Service for Gmail API operations (fetch emails, attachments, etc.)
   * @param authService - Service for user authentication and authorization
   * @param dialogRef - Reference to the Material Dialog containing this component
   * @param data - Data passed from the parent component when opening the dialog
   */
  constructor(
    private gmailService: GmailService,
    private authService: OAuthService,
    public dialogRef: MatDialogRef<EmailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // Log the source context for debugging purposes
    if (data?.source) {
      console.log(`Emails component opened from: ${data.source}`);
    }
  }

  /**
   * Angular lifecycle hook - called after component initialization
   * Automatically loads emails if user is authenticated
   */
  ngOnInit(): void {
    if (this.authService.checkAuthStatus()) {
      this.loadEmailsFromServer();
    }
  }

  // ===== EMAIL MANAGEMENT METHODS =====

  /**
   * Fetches all email messages from Gmail API and populates the emails array
   * This method calls the Gmail service to retrieve emails and handles success/error responses
   */
  loadEmailsFromServer(): void {
    this.gmailService.getEmails().subscribe({
      next: (emails) => {
        this.emails = emails;
      },
      error: (error) => {
        console.error('Failed to load emails:', error);
      }
    });
  }

  // ===== ATTACHMENT SELECTION METHODS =====

  /**
   * Toggles the selection state of an email attachment
   * If attachment is already selected, removes it from selection; otherwise adds it
   * @param attachment - The email attachment to toggle
   * @param emailId - Optional email ID to associate with the attachment
   */
  toggleAttachmentSelection(attachment: EmailAttachment, emailId?: string): void {
    if (emailId) {
      attachment.emailId = emailId;
    }
    
    this.currentSelectedAttachment = attachment;
    const existingIndex = this.selectedAttachments.findIndex(a => a.attachmentId === attachment.attachmentId);

    if (existingIndex > -1) {
      this.selectedAttachments.splice(existingIndex, 1);
    } else {
      this.selectedAttachments.push(attachment);
    }
    
    console.log('Selected attachments:', this.selectedAttachments);
  }

  /**
   * Checks if a specific attachment is currently selected
   * @param attachment - The attachment to check
   * @returns true if the attachment is in the selected attachments array
   */
  isAttachmentSelected(attachment: EmailAttachment): boolean {
    return this.selectedAttachments.some(a => a.attachmentId === attachment.attachmentId);
  }

  /**
   * Selects all available attachments from all emails at once
   * Useful for bulk operations on all attachments
   */
  selectAllAttachments(): void {
    const allAttachments = this.getAllAttachments();
    this.selectedAttachments = [...allAttachments];
    console.log('Selected all attachments:', this.selectedAttachments);
  }

  /**
   * Clears all selected attachments and resets the current selection
   * Useful for resetting the selection state
   */
  clearSelection(): void {
    this.selectedAttachments = [];
    this.currentSelectedAttachment = null;
    console.log('Cleared all selections');
  }

  /**
   * Removes a specific attachment from the selected attachments array
   * @param attachment - The attachment to remove from selection
   */
  removeFromSelection(attachment: EmailAttachment): void {
    const index = this.selectedAttachments.findIndex(a => a.attachmentId === attachment.attachmentId);
    if (index > -1) {
      this.selectedAttachments.splice(index, 1);
      console.log('Removed attachment from selection:', attachment.fileName);
    }
  }

  /**
   * Retrieves all attachments from all loaded emails
   * Ensures each attachment has the emailId property set for reference
   * @returns Array of all available email attachments
   */
  getAllAttachments(): EmailAttachment[] {
    const allAttachments: EmailAttachment[] = [];
    this.emails.forEach(email => {
      if (email.attachments && email.attachments.length > 0) {
        email.attachments.forEach(attachment => {
          attachment.emailId = email.id; // Ensure emailId is set
          allAttachments.push(attachment);
        });
      }
    });
    return allAttachments;
  }

  // ===== PDF DISPLAY METHODS =====

  /**
   * Displays PDF attachments for viewing without processing
   * Takes currently selected attachments and fetches their content for display
   */
  displayPdfAttachment(): void {
    if (this.selectedAttachments.length > 0) {
      this.fetchAndDisplayMultiplePdfs(this.selectedAttachments);
    }
  }

  /**
   * Displays and processes PDF attachments for analysis
   * Fetches attachment content and sends it for processing/analysis
   */
  displayAndProcessPdfAttachment(): void {
    if (this.selectedAttachments.length > 0) {
      this.processMultipleAttachmentsForAnalysis(this.selectedAttachments);
    }
  }

  /**
   * Fetches and displays a single PDF attachment (used for context menu actions)
   * @param emailId - The ID of the email containing the attachment
   * @param attachment - The attachment to display
   */
  fetchAndDisplayPdf(emailId: string, attachment: EmailAttachment): void {
    // For single attachment (context menu)
    this.fetchAndDisplayMultiplePdfs([attachment]);
  }

  /**
   * Fetches attachment data from server and creates PDF URLs for display
   * Handles multiple attachments simultaneously and manages PDF URL creation/cleanup
   * @param attachments - Array of attachments to fetch and display
   */
  private fetchAndDisplayMultiplePdfs(attachments: EmailAttachment[]): void {
    this.gmailService.getAttachment(attachments).subscribe({
      next: (updatedAttachments) => {
        if (updatedAttachments && updatedAttachments.length > 0) {
          // Clear previous PDF URLs to prevent memory leaks
          this.cleanupAllPdfUrls();
          
          // Update the original attachments with the fetched data
          updatedAttachments.forEach(updatedAttachment => {
            const originalAttachment = attachments.find(a => a.attachmentId === updatedAttachment.attachmentId);
            if (originalAttachment && this.isValidAttachmentData(updatedAttachment.fileData)) {
              originalAttachment.fileData = updatedAttachment.fileData;
              
              // Create PDF URL for each valid attachment
              try {
                const blob = this.convertBase64ToBlob(updatedAttachment.fileData);
                const url = URL.createObjectURL(blob);
                this.displayedPdfUrls.push({
                  attachment: originalAttachment,
                  url: url
                });
              } catch (error) {
                console.error('Failed to create PDF URL for attachment:', originalAttachment.fileName, error);
              }
            }
          });

          // Also set the first PDF for backward compatibility
          if (this.displayedPdfUrls.length > 0) {
            this.displayedPdfUrl = this.displayedPdfUrls[0].url;
          }
          
          console.log(`Displaying ${this.displayedPdfUrls.length} PDF attachments`);
        } else {
          console.error('No valid attachments received');
        }
      },
      error: (error) => {
        console.error('Error fetching attachments:', error);
      }
    });
  }

  /**
   * Prepares multiple attachments for analysis by ensuring they have file data
   * If attachments lack data, fetches it first before sending for processing
   * @param attachments - Array of attachments to process for analysis
   */
  private processMultipleAttachmentsForAnalysis(attachments: EmailAttachment[]): void {
    // Check if attachments have data, if not fetch them first
    const attachmentsWithoutData = attachments.filter(att => !this.isValidAttachmentData(att.fileData));
    
    if (attachmentsWithoutData.length > 0) {
      // First fetch the missing attachment data
      this.gmailService.getAttachment(attachmentsWithoutData).subscribe({
        next: (updatedAttachments) => {
          // Update the original attachments with the fetched data
          updatedAttachments.forEach(updatedAttachment => {
            const originalAttachment = attachments.find(a => a.attachmentId === updatedAttachment.attachmentId);
            if (originalAttachment) {
              originalAttachment.fileData = updatedAttachment.fileData;
            }
          });
          
          // Now process all attachments
          this.sendMultipleAttachmentsForProcessing(attachments);
        },
        error: (error) => {
          console.error('Failed to fetch attachment data before processing:', error);
        }
      });
    } else {
      // All attachments have data, proceed with processing
      this.sendMultipleAttachmentsForProcessing(attachments);
    }
  }

  /**
   * Sends validated attachments to the server for processing/analysis
   * Validates attachments have valid data before sending, displays first PDF if needed
   * @param attachments - Array of attachments with file data to process
   */
  private sendMultipleAttachmentsForProcessing(attachments: EmailAttachment[]): void {
    // Validate all attachments have data
    const validAttachments = attachments.filter(att => this.isValidAttachmentData(att.fileData));
    
    if (validAttachments.length === 0) {
      console.error('No valid attachments to process');
      alert('No valid attachments to process');
      return;
    }

    // Display the first valid PDF if not already displayed
    if (!this.displayedPdfUrl && validAttachments.length > 0) {
      this.createPdfDisplayUrl(validAttachments[0].fileData);
    }

    // Send all valid attachments for processing
    this.gmailService.processAttachment1(validAttachments).subscribe({
      next: (result) => {
        alert(`Processing completed for ${validAttachments.length} attachment(s): ${result}`);
        console.log('Processing result:', result);
      },
      error: (error) => {
        console.error('Processing failed:', error);
        alert(`Failed to process ${validAttachments.length} attachment(s)`);
      }
    });
  }

  /**
   * Creates a PDF display URL from base64 data and sets it for viewing
   * @param base64Data - Base64 encoded PDF file data
   */
  private createPdfDisplayUrl(base64Data: string): void {
    try {
      const blob = this.convertBase64ToBlob(base64Data);
      this.cleanupPreviousPdfUrl();
      this.displayedPdfUrl = URL.createObjectURL(blob);
    } catch (error) {
      console.error('Failed to create PDF display URL:', error);
    }
  }

  /**
   * Ensures a PDF is displayed by creating a URL if one doesn't exist
   * @param base64Data - Base64 encoded PDF file data
   */
  private ensurePdfIsDisplayed(base64Data: string): void {
    if (!this.displayedPdfUrl) {
      this.createPdfDisplayUrl(base64Data);
    }
  }

  // ===== UTILITY METHODS =====

  /**
   * Validates if attachment file data is in correct base64 format
   * @param fileData - The file data string to validate
   * @returns true if data is valid base64, false otherwise
   */
  private isValidAttachmentData(fileData: string | undefined): boolean {
    if (!fileData || typeof fileData !== 'string' || fileData.trim() === '') {
      return false;
    }

    const cleanBase64 = fileData.trim();
    const base64Regex = /^[A-Za-z0-9+/=]+$/;
    
    if (!base64Regex.test(cleanBase64)) {
      console.error('Invalid Base64 string format');
      return false;
    }

    return true;
  }

  /**
   * Converts base64 encoded string to Blob object for PDF viewing
   * @param base64Data - Base64 encoded file data
   * @returns Blob object with PDF MIME type
   */
  private convertBase64ToBlob(base64Data: string): Blob {
    const cleanBase64 = base64Data.trim();
    const byteCharacters = atob(cleanBase64);
    const byteNumbers = new Array(byteCharacters.length);
    
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    
    const byteArray = new Uint8Array(byteNumbers);
    return new Blob([byteArray], { type: 'application/pdf' });
  }

  /**
   * Cleanup method to revoke the previous PDF URL to prevent memory leaks
   */
  private cleanupPreviousPdfUrl(): void {
    if (this.displayedPdfUrl) {
      URL.revokeObjectURL(this.displayedPdfUrl);
      this.displayedPdfUrl = null;
    }
  }

  /**
   * Cleanup method to revoke all PDF URLs to prevent memory leaks
   * Cleans both the multiple PDF URLs array and the single PDF URL
   */
  private cleanupAllPdfUrls(): void {
    // Clean up all PDF URLs
    this.displayedPdfUrls.forEach(pdfData => {
      URL.revokeObjectURL(pdfData.url);
    });
    this.displayedPdfUrls = [];
    
    // Also clean up the single PDF URL for backward compatibility
    this.cleanupPreviousPdfUrl();
  }

  /**
   * Closes the PDF viewer and cleans up all associated URLs
   */
  closePdfViewer(): void {
    this.cleanupAllPdfUrls();
  }

  // ===== UI HELPER METHODS =====

  /**
   * Returns appropriate CSS color class based on attachment MIME type
   * @param mimeType - The MIME type of the attachment
   * @returns CSS class string for color styling
   */
  getAttachmentColor(mimeType: string): string {
    if (mimeType.includes('pdf')) return 'text-red-500';
    if (mimeType.includes('image/')) return 'text-blue-500';
    if (mimeType.includes('word')) return 'text-green-500';
    return 'text-gray-500';
  }

  // ===== FILE OPERATIONS =====

  

  // ===== EVENT HANDLERS =====

  /**
   * Handles PDF viewer errors and logs them for debugging
   * @param error - Error object from PDF viewer
   */
  onPdfViewerError(error: any): void {
    console.error('PDF Viewer error:', error);
  }

  /**
   * Angular lifecycle hook - cleanup when component is destroyed
   * Ensures all PDF URLs are properly cleaned up to prevent memory leaks
   */
  ngOnDestroy(): void {
    this.cleanupAllPdfUrls();
  }
}