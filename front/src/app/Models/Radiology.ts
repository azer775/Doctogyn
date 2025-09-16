import { RadiologyType } from "./RadiologyType";

export class Radiology {
  id!: number;
  date!: Date;
  type!: typeof RadiologyType[keyof typeof RadiologyType];
  comment!: string;
  consultationId!: number;
}