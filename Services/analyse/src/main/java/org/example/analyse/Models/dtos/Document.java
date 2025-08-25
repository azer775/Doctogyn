package org.example.analyse.Models.dtos;

import lombok.Data;
import org.example.analyse.Models.entities.*;

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
