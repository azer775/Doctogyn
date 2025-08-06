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
  
  defaultConfig: any = {
    height: 200,
    width: '50%',
    focus: true,
    tableClassName: 'custom-table',
    toolbar: [
      ['style', ['bold', 'italic', 'underline']],
      ['para', ['ul', 'ol']],
      ['table', ['table']],
      ['insert', ['link', 'picture']]
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
      'custom-table': 'Custom Style'
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