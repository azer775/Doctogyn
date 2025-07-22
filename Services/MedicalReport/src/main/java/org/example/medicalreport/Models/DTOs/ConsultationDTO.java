package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.ConsultationType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationDTO {
    private Long id;
    private LocalDate date;
    private String signsNegates;
    private LocalDate weight;
    private double length;
    private double bmi;
    private double breasts;
    private String vagina;
    private ConsultationType consultationType;
    private Long gynecologySubRecordId;
    private Long fertilitySubRecordId;
    private Long obstetricsRecordId;
}
