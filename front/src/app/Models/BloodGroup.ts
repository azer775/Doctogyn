import { BloodType } from "./BloodType";

export class BloodGroup {
  id!: number;
  date!: Date;
  type!: typeof BloodType[keyof typeof BloodType];
  comment!: string;
  consultationId!: number;
}