import { Allergies, ChirurgicalPathology, FamilialPathology, MedicalPathology } from "./enums";

export class MedicalBackground {
  public id!: number;
  public familialPathology!: FamilialPathology;
  public allergies!: Allergies;
  public medicalPathology!: MedicalPathology;
  public chirurgicalPathology!: ChirurgicalPathology;
  public surgicalApproach!: string;
  public comment!: string;
  public date!: Date;
  public backgroundType!: string; // Assuming this is a string type for the background type
  public medicalRecordId!: number;

  constructor(
    id: number,
    familialPathology: FamilialPathology,
    allergies: Allergies,
    medicalPathology: MedicalPathology,
    chirurgicalPathology: ChirurgicalPathology,
    surgicalApproach: string,
    comment: string,
    date: Date,
    backgroundType: string,
    medicalRecordId: number
  ) {
    this.id = id;
    this.familialPathology = familialPathology;
    this.allergies = allergies;
    this.medicalPathology = medicalPathology;
    this.chirurgicalPathology = chirurgicalPathology;
    this.surgicalApproach = surgicalApproach;
    this.comment = comment;
    this.date = date;
    this.backgroundType = backgroundType;
    this.medicalRecordId = medicalRecordId;
  }
}
