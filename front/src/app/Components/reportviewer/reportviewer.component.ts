import { Component, Inject, OnInit, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { EditorComponent } from '../editor/editor.component';
import { Report } from '../../Models/Report';

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
    @Inject(MAT_DIALOG_DATA) public data: { report: Report }
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
      this.dialogRef.close(this.reportForm.value);
    }
  }
}
