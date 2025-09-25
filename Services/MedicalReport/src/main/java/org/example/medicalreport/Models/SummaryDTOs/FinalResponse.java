package org.example.medicalreport.Models.SummaryDTOs;

import lombok.Data;

import java.util.List;

@Data
public class FinalResponse {
    ResponseType responseType;
    String summary;
    List<UnrecognizedAbbreviation> unrecognizedAbbreviation;
}
