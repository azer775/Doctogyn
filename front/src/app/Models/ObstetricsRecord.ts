import { Consultation } from "./Consultation";
import { ConceptionType } from "./enums";

export class ObstetricsRecord {
  public id!: number;
  public conceptionType!: ConceptionType;
  public conceptionDate!: Date | null;
  public ddr!: Date | null;
  public date!: Date | null;
  public nfoetus!: number;
  public comment!: string;
  public medicalRecordId!: number;
  public consultations!: Consultation[];
}