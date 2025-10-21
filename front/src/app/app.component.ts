import { Component, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JsonPipe } from '@angular/common';
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
import { EchographieFormComponent } from './Components/echographie-form/echographie-form.component';
import { LoginOauthComponent } from "./Components/login-oauth/login-oauth.component";
import { BacteriologyFormComponent } from "./Components/bacteriology-form/bacteriology-form.component";
import { BiologyFormComponent } from "./Components/biology-form/biology-form.component";
import { BiologyListComponent } from './Components/biology-list/biology-list.component';
import { BacteriologyListComponent } from './Components/bacteriology-list/bacteriology-list.component';
import { BloodGroupListComponent } from './Components/blood-group-list/blood-group-list.component';
import { RadiologyListComponent } from './Components/radiology-list/radiology-list.component';
import { SpermAnalysisListComponent } from './Components/sperm-analysis-list/sperm-analysis-list.component';
import { AnalysesListComponent } from "./Components/analyses-list/analyses-list.component";
import { ExtractionResponse } from './Models/ExtractionResponse';
import { Document } from './Models/Document';
import { Biology } from './Models/Biology';
import { Bacteriology } from './Models/Bacteriology';
import { BloodGroup } from './Models/BloodGroup';
import { Radiology } from './Models/Radiology';
import { Serology } from './Models/Serology';
import { SpermAnalysis } from './Models/SpermAnalysis';
import { BiologyType, BiologyInterpretation } from './Models/BiologyEnums';
import { BacteriologyType, Germ, BacteriologyInterpretation } from './Models/BacteriologyEnums';
import { BloodType } from './Models/BloodType';
import { RadiologyType } from './Models/RadiologyType';
import { SerologyType, SerologyInterpretation } from './Models/SerologyEnums';
import { SpermNorm } from './Models/SpermNorm';
import { EmailsComponent } from "./Components/emails/emails.component";
import { getBiologyTypeById } from './Models/BiologyEnums';
import { EmailAnalysesListComponent } from './Components/email-analyses-list/email-analyses-list.component';
import { SummaryComponent } from "./Components/summary/summary.component";
import { LoginComponent } from "./Components/login/login.component";
import { NavbarComponent } from "./Components/navbar/navbar.component";
import { MedicalRecordsListComponent } from "./Components/medical-records-list/medical-records-list.component";
import { CrewFormComponent } from "./Components/crew-form/crew-form.component";
import { CrewTabComponent } from "./Components/crew-tab/crew-tab.component";
import { SettingsComponent } from "./Components/settings/settings.component";
import { Scheduler2Component } from "./Components/scheduler2/scheduler2.component";
import { AppointmentFormComponent } from "./Components/appointment-form/appointment-form.component";
import { DashboardComponent } from "./Components/dashboard/dashboard.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [AnalysesListComponent, EmailsComponent, LoginOauthComponent, EchographieFormComponent, EmailAnalysesListComponent, TabsComponent, ConsultationFormComponent, SummaryComponent, LoginComponent, NavbarComponent, MedicalRecordFormComponent, MedicalRecordsListComponent, RouterOutlet, CrewFormComponent, CrewTabComponent, SettingsComponent, Scheduler2Component, AppointmentFormComponent, EditorComponent, DashboardComponent, ReactiveFormsModule, JsonPipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'front';
  editorForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.editorForm = this.fb.group({
      content: ['<h2>Test Medical Report</h2><h3>Patient Information</h3><p><strong>Name:</strong> John Doe</p><p><strong>Date:</strong> October 11, 2025</p><h3>Examination Results</h3><table><thead><tr><th>Test</th><th>Result</th><th>Reference Range</th></tr></thead><tbody><tr><td>Hemoglobin</td><td>14.5 g/dL</td><td>13.5-17.5 g/dL</td></tr><tr><td>White Blood Cells</td><td>7,500 /µL</td><td>4,500-11,000 /µL</td></tr><tr><td>Platelets</td><td>250,000 /µL</td><td>150,000-400,000 /µL</td></tr></tbody></table><h3>Notes</h3><ul><li>All results within normal range</li><li>Patient appears healthy</li><li>Follow-up in 6 months</li></ul><h4>Recommendations</h4><p>Continue current medication and maintain a healthy lifestyle.</p>']
    });
  }
  
  // Sample ExtractionResponse to test analyses-list component
  sampleExtractionResponse: ExtractionResponse = {
    documents: [
      {
        biologies: [
          {
            id: 1,
            date: new Date('2024-01-15'),
            type: BiologyType.ALANINE_AMINOTRANSFERASE_IU_L,
            value: 95,
            interpretation: BiologyInterpretation.NORMAL,
            comment: 'Normal fasting glucose level',
            consultationId: 1
          },
          {
            id: 2,
            date: new Date('2024-01-15'),
            type: getBiologyTypeById(-55)!, 
            value: 6.2,
            interpretation: BiologyInterpretation.NORMAL,
            comment: 'Good glycemic control',
            consultationId: 1
          }
        ],
        bacteriologies: [
          {
            id: 1,
            date: new Date('2024-01-10'),
            type: BacteriologyType.URINE_CULTURE,
            germs: [Germ.ESCHERICHIA_COLI.id, Germ.STAPHYLOCOCCUS_AUREUS.id],
            interpretation: BacteriologyInterpretation.POSITIVE,
            comment: 'UTI detected',
            consultationId: 1
          }
        ],
        bloodgroups: [
          {
            id: 1,
            date: new Date('2024-01-05'),
            type: BloodType.A_POSITIVE,
            comment: 'Blood type confirmed',
            consultationId: 1
          }
        ],
        radiologies: [
          {
            id: 1,
            date: new Date('2024-01-20'),
            type: RadiologyType.PELVIC_SCAN,
            comment: 'No abnormalities detected',
            consultationId: 1
          }
        ],
        serologies: [
          {
            id: 1,
            date: new Date('2024-01-12'),
            type: SerologyType.RUBELLA_SEROLOGY_IGG,
            value: 250,
            interpretation: SerologyInterpretation.POSITIVE,
            comment: 'Immune to rubella',
            consultationId: 1
          },
          {
            id: 2,
            date: new Date('2024-01-12'),
            type: SerologyType.TOXOPLASMA_SEROLOGY_IGG,
            value: 5,
            interpretation: SerologyInterpretation.NEGATIVE,
            comment: 'Not immune to toxoplasma',
            consultationId: 1
          }
        ],
        spermAnalyses: [
          {
            id: 1,
            date: new Date('2024-01-25'),
            abstinence: 3,
            ph: 7.8,
            volume: 3.5,
            concentration: 25,
            progressivemobility: 45,
            totalmotility: 65,
            totalcount: 87.5,
            roundcells: 2,
            leukocytes: 1,
            morphology: 8,
            norm: SpermNorm.DAVID,
            vitality: 85,
            tms: 40,
            consultationId: 1
          }
        ]
      }
    ]
  };

  medicalBackgrounds: MedicalBackground[] = [
  new MedicalBackground(1, FamilialPathology.OVARIAN_CANCER, Allergies.Drug, MedicalPathology.BoneCancer, ChirurgicalPathology.APPENDECTOMY, '', 'Family history of ovarian cancer', new Date('2023-01-05'), 'Familial', 1),
  new MedicalBackground(2, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.Depression, ChirurgicalPathology.APPENDECTOMY, '', 'Peanut allergy', new Date('2023-05-10'), 'Allergies', 1),
  new MedicalBackground(3, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.HeartDisease, ChirurgicalPathology.APPENDECTOMY, '', 'Diagnosed with hypertension', new Date('2022-11-15'), 'Medical', 1),
  new MedicalBackground(4, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.BoneCancer, ChirurgicalPathology.APPENDECTOMY, '', 'Appendix removal surgery', new Date('2021-03-22'), 'Chirurgical', 1),
  new MedicalBackground(5, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.Depression, ChirurgicalPathology.APPENDECTOMY, '', 'Maternal breast cancer history', new Date('2022-01-01'), 'Familial', 1),
  new MedicalBackground(6, FamilialPathology.COLON_CANCER, Allergies.Drug, MedicalPathology.BoneCancer, ChirurgicalPathology.APPENDECTOMY, '', 'Penicillin reaction', new Date('2024-01-30'), 'Allergies', 1)
]

}
