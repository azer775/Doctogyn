import { Consultation } from "./Consultation";
import { CivilState, Duration } from "./enums";

export class FertilitySubRecord {
  public id!: number;
  public age!: number;
  public infertility!: Date | null;
  public date!: Date | null;
  public duration!: Duration; // Using string for Duration; clarify actual type
  public cycleLength!: number;
  public cycleMin!: number;
  public cycleMax!: number;
  public dysmenorrhea!: boolean;
  public menorrhagia!: boolean;
  public metrorrhagia!: boolean;
  public civilState!: CivilState;
  public comment!: string;
  public medicalRecordId!: number;
  public consultations!: Consultation[];
}