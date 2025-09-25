package org.example.medicalreport.Models.SummaryDTOs;

import lombok.Data;

import java.util.List;

@Data
public class SummaryRequest {
    String text;
    List<AbbreviationDefinition> abbreviations;
}
