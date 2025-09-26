import { AbbreviationDefinition } from "./AbbreviationDefinition";

export class SummaryRequest {
    text!: string;
    abbreviations!: AbbreviationDefinition[];
}