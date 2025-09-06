import { SpermNorm } from "./SpermNorm";

export class SpermAnalysis {
  id!: number;
  date!: Date;
  abstinence!: number;
  ph!: number;
  volume!: number;
  concentration!: number;
  progressivemobility!: number;
  totalmotility!: number;
  totalcount!: number;
  roundcells!: number;
  leukocytes!: number;
  morphology!: number;
  norm!: typeof SpermNorm[keyof typeof SpermNorm];
  vitality!: number;
  tms!: number;
  consultationId!: number;
}