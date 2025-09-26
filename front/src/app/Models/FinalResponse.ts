import { ResponseType } from "./enums";
import { UnrecognizedAbbreviation } from "./UnrecognizedAbbreviation";
export class FinalResponse {
    responseType!: ResponseType;
    summary?: string;
    unrecognizedAbbreviation?: UnrecognizedAbbreviation[];
}
