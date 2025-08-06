import { Component, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MedicalBackgroundFormComponent } from './Components/medical-background-form/medical-background-form.component';
import { MedicalBackgroundListComponent } from "./Components/medical-background-list/medical-background-list.component";
import { MedicalRecordFormComponent } from "./Components/medical-record-form/medical-record-form.component";
import { GynecologySubRecordFormComponent } from "./Components/gynecology-sub-record-form/gynecology-sub-record-form.component";
import { ObstetricsRecordFormComponent } from "./Components/obstetrics-record-form/obstetrics-record-form.component";
import { FertilitySubRecordFormComponent } from "./Components/fertility-sub-record-form/fertility-sub-record-form.component";
import { EditorComponent } from './Components/editor/editor.component';
import { NgxSummernoteModule } from 'ngx-summernote';
import { FormGroup, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ConsultationFormComponent } from './Components/consultation-form/consultation-form.component';
import { TabsComponent } from "./Components/tabs/tabs.component";
import { GynecologySubRecordDetailComponent } from "./Components/gynecology-sub-record-detail/gynecology-sub-record-detail.component";
import { ObstetricsRecordDetailComponent } from './Components/obstetrics-record-detail/obstetrics-record-detail.component';
import { FertilitySubRecordDetailComponent } from "./Components/fertility-sub-record-detail/fertility-sub-record-detail.component";
import { MedicalRecordPreviewComponent } from "./Components/medical-record-preview/medical-record-preview.component";
import { ConsultationDetailComponent } from "./Components/consultation-detail/consultation-detail.component";
import { ConsultationListComponent } from "./Components/consultation-list/consultation-list.component";
import { MedicalBackgroundTableComponent } from "./Components/medical-background-table/medical-background-table.component";
import { MedicalBackground } from './Models/MedicalBackground';
import { Allergies, ChirurgicalPathology, FamilialPathology, MedicalPathology } from './Models/enums';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MedicalBackgroundFormComponent, MedicalBackgroundListComponent, MedicalRecordFormComponent, GynecologySubRecordFormComponent, ObstetricsRecordFormComponent, FertilitySubRecordFormComponent, EditorComponent, NgxSummernoteModule, ReactiveFormsModule, ConsultationFormComponent, TabsComponent, GynecologySubRecordDetailComponent, ObstetricsRecordDetailComponent, FertilitySubRecordDetailComponent, MedicalRecordPreviewComponent, ConsultationDetailComponent, ConsultationListComponent, MedicalBackgroundTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'front';
  medicalBackgrounds: MedicalBackground[] = [
  new MedicalBackground(1, FamilialPathology.OVARIAN_CANCER, Allergies.NONE, MedicalPathology.BONE_CANCER, ChirurgicalPathology.APPENDECTOMY, '', 'Family history of ovarian cancer', new Date('2023-01-05'), 'Familial', 1),
  new MedicalBackground(2, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.DEPRESSION, ChirurgicalPathology.APPENDECTOMY, '', 'Peanut allergy', new Date('2023-05-10'), 'Allergies', 1),
  new MedicalBackground(3, FamilialPathology.COLON_CANCER, Allergies.NONE, MedicalPathology.HEART_DISEASE, ChirurgicalPathology.APPENDECTOMY, '', 'Diagnosed with hypertension', new Date('2022-11-15'), 'Medical', 1),
  new MedicalBackground(4, FamilialPathology.COLON_CANCER, Allergies.NONE, MedicalPathology.HEPATITIS_B, ChirurgicalPathology.APPENDECTOMY, '', 'Appendix removal surgery', new Date('2021-03-22'), 'Chirurgical', 1),
  new MedicalBackground(5, FamilialPathology.COLON_CANCER, Allergies.NONE, MedicalPathology.DEPRESSION, ChirurgicalPathology.APPENDECTOMY, '', 'Maternal breast cancer history', new Date('2022-01-01'), 'Familial', 1),
  new MedicalBackground(6, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.BONE_CANCER, ChirurgicalPathology.APPENDECTOMY, '', 'Penicillin reaction', new Date('2024-01-30'), 'Allergies', 1)
]

}
