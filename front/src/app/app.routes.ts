import { Routes } from '@angular/router';
import { TabsComponent } from './Components/tabs/tabs.component';

export const routes: Routes = [
  { path: 'medical-record', component: TabsComponent },
  { path: 'medical-record/:id', component: TabsComponent },
];
