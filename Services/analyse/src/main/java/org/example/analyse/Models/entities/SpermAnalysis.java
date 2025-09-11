package org.example.analyse.Models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.analyse.Models.enums.SpermNorm;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SpermAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    SpermNorm norm;
    double vitality;
    double tms;
    long consultationId;
}
