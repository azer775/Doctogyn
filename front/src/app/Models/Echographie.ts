import { Size, UterusSize } from "./enums";

export class Echographie {
  public id!: number;
  public size!: Size;
  public date!: Date;
  public report!: string;
  public cycleDay!: string;
  public condition!: string;
  public uterusSize!: UterusSize;
  public endometrium!: string;
  public myometre!: string;
  public roSize!: string;
  public roComment!: string;
  public loComment!: string;
  public consultationId!: number | null;
}