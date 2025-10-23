import { Component, Inject, OnInit, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { EditorComponent } from '../editor/editor.component';
import { Report } from '../../Models/Report';
import { ReportService } from '../../Services/report.service';

@Component({
  selector: 'app-reportviewer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, EditorComponent],
  templateUrl: './reportviewer.component.html',
  styleUrl: './reportviewer.component.css'
})
export class ReportviewerComponent implements OnInit, AfterViewInit {
  reportForm: FormGroup;
  editorConfig: any = {
    height: '100%',
    width: '100%',
    focus: true,
    toolbar: [
      ['style', ['bold', 'italic', 'underline']],
      ['font', ['strikethrough', 'superscript', 'subscript']],
      ['fontsize', ['fontsize']],
      ['color', ['color']],
      ['para', ['ul', 'ol', 'paragraph']],
      ['height', ['height']],
      ['table', ['table']],
      ['insert', ['link', 'picture']],
      ['view', ['fullscreen', 'codeview']]
    ]
  };

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<ReportviewerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { report: Report },
    private reportService: ReportService
  ) {
    this.reportForm = this.fb.group({
      html: [this.data?.report?.html || '', Validators.required]
    });
  }

  ngOnInit(): void {
    // Form is already initialized in constructor with the data
  }

  ngAfterViewInit(): void {
    // Set the value again after view is initialized to ensure editor is ready
    if (this.data?.report?.html) {
      setTimeout(() => {
        this.reportForm.get('html')?.setValue(this.data.report.html);
      }, 200);
    }
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onSave(): void {
    if (this.reportForm.valid) {
      const htmlContent = this.reportForm.get('html')?.value;
      
      // Call the PDF generation service
      this.reportService.generatePdf(htmlContent).subscribe({
        next: (pdfBlob) => {
          console.log('PDF generated successfully');
          
          // Create a URL for the blob
          const url = window.URL.createObjectURL(pdfBlob);
          
          // Open PDF in a new tab
          window.open(url, '_blank');
          
          // Optionally, also trigger download
          const link = document.createElement('a');
          link.href = url;
          link.download = `medical-report-${new Date().getTime()}.pdf`;
          link.click();
          
          // Clean up the URL object
          window.URL.revokeObjectURL(url);
          
          // Close the dialog after successful generation
          this.dialogRef.close({ success: true, html: htmlContent });
        },
        error: (error) => {
          console.error('Error generating PDF:', error);
          alert('Failed to generate PDF. Please try again.');
        }
      });
    }
  }
}
