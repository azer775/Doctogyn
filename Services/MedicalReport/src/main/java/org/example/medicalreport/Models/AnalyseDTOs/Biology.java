package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Biology {
    long id;
    LocalDate date;
    int type;
    double value;
    int interpretation;
    String comment;
    long consultationId;
}
