package org.example.medicalreport.Models.SummaryDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AbbreviationDefinition {
    String abbreviation;
    String meaning;
}
