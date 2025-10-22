import { AnalyseReport } from "./AnalyseReport";
import { FertilitySubRecord } from "./FertilitySubRecord";
import { GynecologySubRecord } from "./GynecologySubRecord";
import { ObstetricsRecord } from "./ObstetricsRecord";

export class ReportRequest {
    fertilitySubRecords!: FertilitySubRecord[];
    gynecologySubRecords!: GynecologySubRecord[];
    obstetricsRecords!: ObstetricsRecord[];
    familialBackground!: boolean;   
    medicalBackground!: boolean;
    chirurgicalBackground!: boolean;
    allergiesBackground!: boolean;
    echography!: boolean;
    analyseReport!: AnalyseReport;
}