import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, forwardRef, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { NgxSummernoteModule } from 'ngx-summernote';
import { DomSanitizer } from '@angular/platform-browser';

 declare var $: any;
 @Component({
  selector: 'app-editor',
  standalone: true,
  imports: [NgxSummernoteModule, FormsModule, CommonModule],
  templateUrl: './editor.component.html',
  styleUrl: './editor.component.css',
  providers: [
  {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => EditorComponent),
    multi: true
  }
  ]
})
export class EditorComponent implements OnInit, OnChanges, ControlValueAccessor {
  @Input() content: string = ''; // Initial HTML content (optional, for non-form usage)
  @Input() config: any = {}; // Custom Summernote configuration
  @Output() mediaDelete = new EventEmitter<any>(); // Emits media deletion events
  instanceId: string = `editor-${Math.random().toString(36).substr(2, 9)}`; // Unique ID for each instance

  private onChange: (value: string) => void = () => {};
  private onTouched: () => void = () => {};
  private defaultEditorCss: string = `
    body {
      font-family: Arial, Helvetica, sans-serif;
      margin: 40px;
      font-size: 12pt;
      color: #333333;
      line-height: 1.4;
    }
    h2 {
      font-size: 18pt;
      color: #1a3c6d;
      text-align: center;
      margin-bottom: 20px;
      border-bottom: 2px solid #1a3c6d;
      padding-bottom: 5px;
    }
    h3 {
      font-size: 14pt;
      color: #2e5a8a;
      margin-top: 20px;
      margin-bottom: 10px;
    }
    h4 {
      font-size: 12pt;
      color: #4a4a4a;
      margin-top: 15px;
      margin-bottom: 8px;
      font-weight: bold;
    }
    p {
      margin: 5px 0;
      font-size: 11pt;
    }
    strong {
      font-weight: bold;
      color: #333333;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin: 10px 0;
      font-size: 11pt;
    }
    th, td {
      border: 1px solid #999999;
      padding: 8px;
      text-align: left;
    }
    th {
      background-color: #e6f0fa;
      font-weight: bold;
      color: #1a3c6d;
    }
    tr:nth-child(even) {
      background-color: #f9f9f9;
    }
    ul {
      list-style-type: disc;
      margin: 10px 0 10px 20px;
      font-size: 11pt;
    }
    .custom-table {
      width: auto;
      min-width: 50%;
      border-collapse: collapse;
      margin: 10px 0;
    }
    .custom-table th, .custom-table td {
      border: 1px solid #999999;
      padding: 8px;
    }
    .custom-table th {
      background-color: #e6f0fa;
    }
    div {
      margin-bottom: 15px;
    }
    p p {
      margin: 0;
      display: inline;
    }
    h3, h2 {
      page-break-after: avoid;
    }
    table {
      page-break-inside: auto;
    }
    tr {
      page-break-inside: avoid;
      page-break-after: auto;
    }
  ;`
  defaultConfig: any = {
    height: 200,
    width: '50%',
    focus: true,
    //tableClassName: 'custom-table',
    toolbar: [
      ['style', ['bold', 'italic', 'underline']],
      ['para', ['ul', 'ol']],
      ['table', ['table']],
      ['insert', ['link', 'picture']],
      ['para', ['style0', 'ul', 'ol', 'paragraph', 'height']]
    ],
    popover: {
      table: [
        ['add', ['addRowDown', 'addRowUp', 'addColLeft', 'addColRight']],
        ['delete', ['deleteRow', 'deleteCol', 'deleteTable']],
        ['custom', ['tableStyles']]
      ]

    },
    tableStyles: {
      'table-striped': 'Striped',
      'table-bordered': 'Bordered',
     // 'custom-table': 'Custom Style'
    },
    callbacks: {
      onChange: (contents: string) => {
        this.onChange(contents); // Update form control value
        this.onTouched(); // Mark as touched
        this.content = contents; // Keep local content in sync
      }
    }
  };

  ngOnInit() {
    // Merge default config with provided config, ensuring callbacks are merged properly
    this.config = {
      ...this.defaultConfig,
      ...this.config,
      callbacks: {
        ...this.defaultConfig.callbacks,
        ...(this.config.callbacks || {})
      }
    };
  }

  ngOnChanges(changes: SimpleChanges) {
    // Update content if input changes (for non-form usage)
    if (changes['content'] && changes['content'].currentValue) {
      this.content = changes['content'].currentValue;
      this.onChange(this.content); // Update form control if used in a form
    }
  }

  onMediaDelete(event: any) {
    this.mediaDelete.emit(event);
  }

  getContent(): string {
    return document.getElementById(this.instanceId)?.innerHTML || '';
  }

  getEditorContent(): string {
    if (typeof $ !== 'undefined' && $(`#${this.instanceId}`).summernote) {
      const content = $(`#${this.instanceId}`).summernote('code');
      this.content = content; // Keep local content in sync
      return content;
    }
    return this.content;
  }

  logContent() {
    const content = this.getEditorContent();
    console.log('Editor Content:', content);
  }

  // ControlValueAccessor methods
  writeValue(value: string): void {
    this.content = value || '';
    // Update Summernote editor content if initialized
    if (typeof $ !== 'undefined' && $(`#${this.instanceId}`).summernote) {
      $(`#${this.instanceId}`).summernote('code', this.content);
    }
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  
}