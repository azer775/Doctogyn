import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AuthentificationService } from '../../Services/authentification.service';
import { Crew } from '../../Models/Crew';
import { CrewFormComponent } from '../crew-form/crew-form.component';

@Component({
  selector: 'app-crew-tab',
  standalone: true,
  imports: [CommonModule, MatDialogModule, CrewFormComponent],
  templateUrl: './crew-tab.component.html',
  styleUrl: './crew-tab.component.css'
})
export class CrewTabComponent implements OnInit {
  crews: Crew[] = [];
  isLoading = true;

  constructor(
    private authentificationService: AuthentificationService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadCrews();
  }

  loadCrews(): void {
    this.isLoading = true;
    this.authentificationService.getAllCrews().subscribe({
      next: (crews) => {
        this.crews = crews;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading crews:', error);
        this.isLoading = false;
      }
    });
  }

  openCrewForm(): void {
    const dialogRef = this.dialog.open(CrewFormComponent, {
      width: '600px',
      maxWidth: '90vw',
      maxHeight: '90vh',
      autoFocus: true,
      panelClass: 'custom-dialog-container',
    });

    dialogRef.afterClosed().subscribe(() => {
      this.loadCrews(); // Refresh the crew list after dialog closes
    });
  }

  deleteCrew(id: number): void {
    if (confirm('Are you sure you want to delete this crew member?')) {
      this.authentificationService.deleteCrew(id).subscribe({
        next: () => {
          this.loadCrews();
        },
        error: (error) => {
          console.error('Error deleting crew:', error);
        }
      });
    }
  }

  lockUnlockCrew(id: number): void {
    this.authentificationService.lockUnlockCrew(id).subscribe({
      next: () => {
        this.loadCrews();
      },
      error: (error) => {
        console.error('Error locking/unlocking crew:', error);
      }
    });
  }
}
