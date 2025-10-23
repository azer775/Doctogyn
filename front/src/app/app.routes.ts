import { Routes } from '@angular/router';
import { TabsComponent } from './Components/tabs/tabs.component';
import { LoginComponent } from './Components/login/login.component';
import { MedicalRecordsListComponent } from './Components/medical-records-list/medical-records-list.component';
import { Scheduler2Component } from './Components/scheduler2/scheduler2.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { SettingsComponent } from './Components/settings/settings.component';
import { StatsComponent } from './Components/stats/stats.component';
import { authGuard } from './Services/auth.guard';
import { roleGuard } from './Services/role.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [authGuard],
    children: [
      { 
        path: 'medical-records-list', 
        component: MedicalRecordsListComponent,
        canActivate: [roleGuard],
        data: { roles: ['ADMIN', 'DOCTOR'] } // Not accessible by SECRETARY
      },
      { 
        path: 'medical-record', 
        component: TabsComponent,
        canActivate: [roleGuard],
        data: { roles: ['ADMIN', 'DOCTOR'] } // Not accessible by SECRETARY
      },
      { 
        path: 'medical-record/:id', 
        component: TabsComponent,
        canActivate: [roleGuard],
        data: { roles: ['ADMIN', 'DOCTOR'] } // Not accessible by SECRETARY
      },
      { 
        path: 'scheduler2', 
        component: Scheduler2Component 
        // Accessible by all roles (no guard)
      },
      { 
        path: 'settings', 
        component: SettingsComponent,
        canActivate: [roleGuard],
        data: { roles: ['ADMIN', 'DOCTOR'] } // Not accessible by SECRETARY
      },
      { 
        path: 'stats', 
        component: StatsComponent,
        canActivate: [roleGuard],
        data: { roles: ['ADMIN'] } // Only accessible by ADMIN
      },
      { path: '', redirectTo: 'scheduler2', pathMatch: 'full' }
    ]
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: '/dashboard' }
]; 
