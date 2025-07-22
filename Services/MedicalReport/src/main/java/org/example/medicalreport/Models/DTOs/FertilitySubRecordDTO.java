package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.CivilState;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FertilitySubRecordDTO {
    private Long id;
    private int age;
    private LocalDate infertility;
    private Duration duration;
    private int cycleLength;
    private int cycleMin;
    private int cycleMax;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private CivilState civilState;
    private String comment;
    private Long medicalRecordId;
}
