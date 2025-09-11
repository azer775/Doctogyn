package org.example.medicalreport.Models.AnalyseDTOs;

import java.time.LocalDate;

public class Radiology {
    long id;
    LocalDate date;
    int type;
    String conclusion;
    String comment;
    long consultationId;
}
