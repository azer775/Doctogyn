import { Allergies, ChirurgicalPathology, FamilialPathology, MedicalPathology } from "./enums";

export class MedicalBackground {
  public id!: number | null; // Assuming id can be null for new records
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
    id: number | null,
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
    this.id = id || null;
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
