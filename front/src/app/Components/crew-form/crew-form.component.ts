import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialogRef } from '@angular/material/dialog';
import { Crew } from '../../Models/Crew';
import { Role } from '../../Models/enums';
import { AuthentificationService } from '../../Services/authentification.service';
import { Cabinet } from '../../Models/Cabinet';

@Component({
  selector: 'app-crew-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crew-form.component.html',
  styleUrl: './crew-form.component.css'
})
export class CrewFormComponent implements OnInit {
  crewForm: FormGroup;
  Roles = Object.values(Role);

  constructor(
    private fb: FormBuilder,
    private authentificationService: AuthentificationService,
    private dialogRef: MatDialogRef<CrewFormComponent>
  ) {
    this.crewForm = this.fb.group({
      nom: ['', [Validators.required, Validators.minLength(2)]],
      prenom: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      pwd: ['', [Validators.required, Validators.minLength(6)]],
      role: [Role.SECRETARY, Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.crewForm.valid) {
      const crew: Crew = {
        id: 0,
        nom: this.crewForm.value.nom,
        prenom: this.crewForm.value.prenom,
        email: this.crewForm.value.email,
        locked: false,
        pwd: this.crewForm.value.pwd,
        role: this.crewForm.value.role,
        cabinet: {} as Cabinet
      };

      this.authentificationService.addCrew(crew)
        .subscribe({
          next: (response) => {
            console.log('Crew member added successfully', response);
            alert('Crew member added successfully!');
            this.dialogRef.close(response); // Close dialog and pass response
          },
          error: (error) => {
            console.error('Error adding crew member', error);
            alert('Error adding crew member. Please try again.');
          }
        });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  private resetForm() {
    this.crewForm.reset({
      nom: '',
      prenom: '',
      email: '',
      pwd: '',
      role: Role.SECRETARY
    });
  }
}
