import { Bacteriology } from "./Bacteriology";
import { Biology } from "./Biology";
import { BloodGroup } from "./BloodGroup";
import { Radiology } from "./Radiology";
import { Serology } from "./Serology";
import { SpermAnalysis } from "./SpermAnalysis";

export class Document {
    biologies!: Biology[];
    bacteriologies!: Bacteriology[];
    bloodgroups!: BloodGroup[];
    radiologies!: Radiology[];
    serologies!: Serology[];
    spermAnalyses!: SpermAnalysis[];
}