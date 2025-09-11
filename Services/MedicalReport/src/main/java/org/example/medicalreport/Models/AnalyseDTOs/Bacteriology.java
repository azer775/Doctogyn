package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Bacteriology {
    long id;
    LocalDate date;
    int type;
    List<Integer> germs = new ArrayList<>();
    int interpretation;
    String comment;
    long consultationId;
}
