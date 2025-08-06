import { Sex, CivilState } from "./enums";
import { FertilitySubRecord } from "./FertilitySubRecord";
import { GynecologySubRecord } from "./GynecologySubRecord";
import { MedicalBackground } from "./MedicalBackground";
import { ObstetricsRecord } from "./ObstetricsRecord";

export class MedicalRecord {
  public id!: number;
  public name!: string;
  public surname!: string;
  public birthDate!: Date;
  public sex!: Sex;
  public civilState!: CivilState;
  public email!: string;
  public comment!: string;
  public fertilitySubRecords!: FertilitySubRecord[];
  public obstetricsRecords!: ObstetricsRecord[];
  public gynecologySubRecords!: GynecologySubRecord[]; 
  public medicalBackgrounds!: MedicalBackground[];
}