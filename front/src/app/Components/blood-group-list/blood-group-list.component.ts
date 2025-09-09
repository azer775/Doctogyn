import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BloodGroupFormComponent } from '../blood-group-form/blood-group-form.component';
import { BloodGroup } from '../../Models/BloodGroup';
import { BloodType } from '../../Models/BloodType';

@Component({
  selector: 'app-blood-group-list',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule, BloodGroupFormComponent],
  templateUrl: './blood-group-list.component.html',
  styleUrl: './blood-group-list.component.css'
})
export class BloodGroupListComponent implements OnInit {
  @Input() bloodGroups: BloodGroup[] = [];
  @Output() bloodGroupsChange = new EventEmitter<BloodGroup[]>();

  ngOnInit(): void {
    // Ensure bloodGroups is initialized as an array and filter out empty entries
    this.bloodGroups = Array.isArray(this.bloodGroups) 
      ? this.bloodGroups.filter(bg => bg && Object.keys(bg).length > 0)
      : [];
    
    // Only emit if there are actual blood groups
    if (this.bloodGroups.length > 0) {
      this.emitBloodGroups();
    }
  }

  addBloodGroupForm(): void {
    const newBloodGroup: BloodGroup = {
      id: 0,
      date: new Date(),
      type: Object.values(BloodType)[0] || null,
      comment: '',
      consultationId: 0
    };
    this.bloodGroups = [...this.bloodGroups, newBloodGroup];
    this.emitBloodGroups();
  }

  deleteBloodGroupForm(index: number): void {
    this.bloodGroups = this.bloodGroups.filter((_, i) => i !== index);
    this.emitBloodGroups();
  }

  updateBloodGroup(updatedBloodGroup: BloodGroup, index: number): void {
    this.bloodGroups = this.bloodGroups.map((bloodGroup, i) =>
      i === index ? { ...updatedBloodGroup, consultationId: 0, id: 0 } : bloodGroup
    );
    this.emitBloodGroups();
  }

  private emitBloodGroups(): void {
    // Emit bloodGroups without consultationId and id
    const bloodGroupsToEmit = this.bloodGroups.map(bloodGroup => ({
      date: bloodGroup.date,
      type: bloodGroup.type,
      comment: bloodGroup.comment,
      consultationId: 0,
      id: 0
    }));
    this.bloodGroupsChange.emit(bloodGroupsToEmit);
  }
}
