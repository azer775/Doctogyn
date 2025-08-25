package org.example.analyse.Models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.analyse.Models.enums.SerologyInterpretation;
import org.example.analyse.Models.enums.SerologyType;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Serology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    SerologyType type;
    double value;
    SerologyInterpretation interpretation;
    String comment;
    long consultationId;
}
