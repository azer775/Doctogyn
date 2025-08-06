import { ConsultationType, Status } from "./enums";

export class Consultation {
  public id!: number;
  public date!: Date;
  public signsNegates!: string;
  public weight!: number | null; // Assuming typo in Java DTO; clarify if number
  public length!: number;
  public bmi!: number;
  public breasts!: Status;
  public examination!: string;
  public vagina!: Status;
  public consultationType!: ConsultationType;
  public gynecologySubRecordId!: number | null;
  public fertilitySubRecordId!: number | null;
  public obstetricsRecordId!: number | null;}