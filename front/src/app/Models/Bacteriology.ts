
import { BacteriologyType, Germ, BacteriologyInterpretation } from './BacteriologyEnums';

export class Bacteriology {
  id!: number;
  date!: Date;
  type!: typeof BacteriologyType[keyof typeof BacteriologyType];
  germs!: number[];
  interpretation!: typeof BacteriologyInterpretation[keyof typeof BacteriologyInterpretation];
  comment!: string;
  consultationId!: number;
}