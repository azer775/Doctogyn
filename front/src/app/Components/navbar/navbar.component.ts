import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthentificationService } from '../../Services/authentification.service';
import { TokenService } from '../../Services/token.service';
import { Role } from '../../Models/enums';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  userRole: string = '';

  constructor(
    private router: Router,
    private authService: AuthentificationService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    const roles = this.tokenService.userRoles;
    if (roles && roles.length > 0) {
      this.userRole = roles[0]; // Get the first role
    }
    console.log('User role:', this.userRole);
  }

  isSecretary(): boolean {
    return this.userRole === Role.SECRETARY || this.userRole === 'SECRETARY';
  }

  isDoctor(): boolean {
    return this.userRole === Role.DOCTOR || this.userRole === 'DOCTOR';
  }

  isAdmin(): boolean {
    return this.userRole === Role.ADMIN || this.userRole === 'ADMIN';
  }

  logout(): void {
    this.authService.logout();
  }
}
