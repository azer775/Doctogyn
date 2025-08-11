import { Diagnosticpresumption, Myometre, Ovary, Pelvicdiagnosticpresumption, Size, UterusSize } from "./enums";

export class Echographie {
    public id!: number;
    public date!: Date;
    public report!: string;
    public cycleDay!: string;
    public condition!: string;
    // Uterus
    public uterusSize!: UterusSize;
    public uterusLength!: number;
    public uterusWidth!: number;
    public myometre!: Myometre;
    public myomesNumber!: number;
    public endometriumThickness!: number;
    public comment!: string;
    // Right Ovary
    public ovaryR!: Ovary;
    public ovaryRSize!: number;
    public cystSizeOR!: number;
    public diagnosticpresumptionsOR!: Diagnosticpresumption[];
    public ovaryRComment!: string;
    // Left Ovary
    public ovaryL!: Ovary;
    public ovaryLSize!: number;
    public cystSizeOL!: number;
    public diagnosticpresumptionsOL!: Diagnosticpresumption[];
    public ovaryLComment!: string;
    // Right Pelvic Mass
    public pelvicMR!: boolean;
    public pmRSize!: number;
    public pelvicdiagnosticpresumptionsR!: Pelvicdiagnosticpresumption[];
    public pmRComment!: string;
    // Left Pelvic Mass
    public pelvicML!: boolean;
    public pmLSize!: number;
    public pelvicdiagnosticpresumptionsL!: Pelvicdiagnosticpresumption[];
    public pmLComment!: string;
    public consultationId!: number | null;
}