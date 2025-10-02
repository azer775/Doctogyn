package org.example.medicalreport.Models.SummaryDTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FinalResponse {
    ResponseType responseType;
    String summary;
    List<UnrecognizedAbbreviation> unrecognizedAbbreviation;
}
