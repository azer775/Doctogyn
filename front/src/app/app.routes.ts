import { Routes } from '@angular/router';
import { TabsComponent } from './Components/tabs/tabs.component';
import { LoginComponent } from './Components/login/login.component';
import { MedicalRecordsListComponent } from './Components/medical-records-list/medical-records-list.component';
import { Scheduler2Component } from './Components/scheduler2/scheduler2.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { SettingsComponent } from './Components/settings/settings.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent , 
    children: [
      { path: 'medical-records-list', component: MedicalRecordsListComponent },
      { path: 'medical-record', component: TabsComponent },
      { path: 'medical-record/:id', component: TabsComponent },
      { path: 'scheduler2', component: Scheduler2Component },
      { path: 'settings', component: SettingsComponent },
      { path: '', redirectTo: 'scheduler2', pathMatch: 'full' }
    ]
  },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: '**', redirectTo: '/dashboard' }
]; 
