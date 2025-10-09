import { Cons } from "rxjs";
import { ConsultationType } from "./enums";
import { MedicalRecord } from "./MedicalRecord";

export class Appointment {
  id?: number;
  date!: Date;
  reason!: string;
  consultationType!: ConsultationType;
  medicalRecord!: MedicalRecord;
  cabinetId!: number;
}