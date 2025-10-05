import { Routes } from '@angular/router';
import { TabsComponent } from './Components/tabs/tabs.component';
import { LoginComponent } from './Components/login/login.component';
import { MedicalRecordsListComponent } from './Components/medical-records-list/medical-records-list.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'medical-records-list', component: MedicalRecordsListComponent },
  { path: 'medical-record', component: TabsComponent },
  { path: 'medical-record/:id', component: TabsComponent },
  { path: '', redirectTo: '/medical-record', pathMatch: 'full' }
];
