import { SerologyType, SerologyInterpretation } from "./SerologyEnums";

export class Serology {
  id!: number;
  date!: Date;
  type!: typeof SerologyType[keyof typeof SerologyType];
  value!: number;
  interpretation!: typeof SerologyInterpretation[keyof typeof SerologyInterpretation];
  comment!: string;
  consultationId!: number;
}