import { Component, EventEmitter, Input, OnInit, Output, HostListener } from '@angular/core';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Bacteriology } from '../../Models/Bacteriology';
import { BacteriologyType, Germ, BacteriologyInterpretation, getGermById } from '../../Models/BacteriologyEnums';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bacteriology-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './bacteriology-form.component.html',
  styleUrl: './bacteriology-form.component.css'
})
export class BacteriologyFormComponent implements OnInit {
  @Input() bacteriology: Bacteriology | null = null;
  @Output() bacteriologySubmitted = new EventEmitter<Bacteriology>();
  bacteriologyForm: FormGroup;

  bacteriologyTypes = Object.values(BacteriologyType);
  germs = Object.values(Germ);
  bacteriologyInterpretations = Object.values(BacteriologyInterpretation);
  
  dropdownOpen: { [key: string]: boolean } = {
    germs: false
  };

  constructor(private fb: FormBuilder) {
    this.bacteriologyForm = this.fb.group({
      id: [0],
      date: [''],
      type: [null],
      germs: [[]],
      interpretation: [null],
      comment: ['']
    });
  }

  ngOnInit(): void {
    if (this.bacteriology) {
      // Extract IDs from germs (whether they're objects or already IDs)
      const germIds = (this.bacteriology.germs || []).map((germ: any) => 
        typeof germ === 'object' ? germ.id : germ
      );
      
      this.bacteriologyForm.patchValue({
        id: this.bacteriology.id,
        date: this.formatDate(this.bacteriology.date),
        type: this.bacteriology.type,
        germs: germIds,
        interpretation: this.bacteriology.interpretation,
        comment: this.bacteriology.comment
      });
    }
  }

  toggleDropdown(field: string) {
    this.dropdownOpen[field] = !this.dropdownOpen[field];
  }

  updateMultiSelect(field: string, value: any, event: Event) {
    const input = event.target as HTMLInputElement;
    const currentValues = this.bacteriologyForm.get(field)?.value || [];
    
    if (input.checked) {
      this.bacteriologyForm.get(field)?.setValue([...currentValues, value.id]);
    } else {
      this.bacteriologyForm.get(field)?.setValue(currentValues.filter((id: number) => id !== value.id));
    }
  }

  getSelectedGermsDisplay(): string {
    const selectedGermIds = this.bacteriologyForm.get('germs')?.value || [];
    if (selectedGermIds.length === 0) {
      return 'Select germs';
    }
    
    return selectedGermIds
      .map((id: number) => getGermById(id)?.name || `Unknown germ ${id}`)
      .join(', ');
  }

  isGermSelected(germ: any): boolean {
    const selectedGermIds = this.bacteriologyForm.get('germs')?.value || [];
    return selectedGermIds.includes(germ.id);
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const target = event.target as HTMLElement;
    if (!target.closest('.analysis-form-dropdown')) {
      this.dropdownOpen['germs'] = false;
    }
  }

  submitForm(): void {
    const formValue = this.bacteriologyForm.value;
    
    // Convert germ IDs back to germ objects
    const germObjects = (formValue.germs || [])
      .map((id: number) => getGermById(id))
      .filter(Boolean); // Remove any undefined results
    
    const bacteriology: Bacteriology = {
      id: formValue.id,
      date: formValue.date ? new Date(formValue.date) : new Date(),
      type: formValue.type,
      germs: germObjects,
      interpretation: formValue.interpretation,
      comment: formValue.comment,
      consultationId: this.bacteriology ? this.bacteriology.consultationId : 0
    };
    this.bacteriologySubmitted.emit(bacteriology);
  }

  private formatDate(date: Date | string): string {
    if (!date) return '';
    const d = typeof date === 'string' ? new Date(date) : date;
    return d.toISOString().split('T')[0];
  }
}