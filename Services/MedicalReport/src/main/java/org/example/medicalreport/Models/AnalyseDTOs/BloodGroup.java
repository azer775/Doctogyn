package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BloodGroup {
    long id;
    LocalDate date;
    int type;
    String comment;
    long consultationId;
}
