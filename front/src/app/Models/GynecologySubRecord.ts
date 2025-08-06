import { Consultation } from "./Consultation";
import { HormoneStatus } from "./enums";

export class GynecologySubRecord {
  public id!: number;
  public work!: string;
  public civilState!: string;
  public hormoneStatus!: HormoneStatus;
  public menopause!: Date | null;
  public dysmenorrhea!: boolean;
  public menorrhagia!: boolean;
  public metrorrhagia!: boolean;
  public periodMax!: number;
  public periodMin!: number;
  public date!: Date;
  public background!: Date | null;
  public medicalRecordId!: number;
  public consultations!: Consultation[];
}