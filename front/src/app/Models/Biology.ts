import { BiologyType, BiologyInterpretation } from "./BiologyEnums";

export class Biology {
  id!: number;
  date!: Date;
  type!: typeof BiologyType[keyof typeof BiologyType];
  value!: number;
  interpretation!: typeof BiologyInterpretation[keyof typeof BiologyInterpretation];
  comment!: string;
  consultationId!: number;
}