package org.example.medicalreport.Models.AnalyseDTOs;

import lombok.Data;

import java.util.List;

@Data
public class Document {
    List<Biology> biologies;
    List<Bacteriology> bacteriologies;
    List<BloodGroup> bloodgroups;
    List<Radiology> radiologies;
    List<Serology> serologies;
    List<SpermAnalysis> spermAnalyses;
}
