export interface EmailAttachment {
    fileName: string;
    mimeType: string;
    fileData: string; // Base64 string
    fileSize: number;
    attachmentId: string;
    emailId: string;
  }