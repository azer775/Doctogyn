package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.util.List;

@Data
public class ExtractionResponse {
    List<Document> documents;
}
