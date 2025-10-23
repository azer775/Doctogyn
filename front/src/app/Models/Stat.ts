import { ConsultationType } from "./enums";

export class Stat{
    date!: Date;
    echographyCount!: number;
    doctorId!: number;
    consultationType!: ConsultationType;
}