package org.example.medicalreport.Models.SummaryDTOs;

import lombok.Data;

import java.util.List;

@Data
public class UnrecognizedAbbreviation {
    String abbreviation;
    List<String> possibleMeanings;
}
