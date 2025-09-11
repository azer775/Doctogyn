package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SpermAnalysis {
    long id;
    LocalDate date;
    int abstinence;
    double ph;
    double volume;
    double concentration;
    double progressivemobility;
    double totalmotility;
    double totalcount;
    double roundcells;
    double leukocytes;
    double morphology;
    int norm;
    double vitality;
    double tms;
    long consultationId;
}
