package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.ConsultationType;
import org.example.medicalreport.Models.enums.State;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultationDTO {
    private Long id;
    private LocalDate date;
    private String signsNegates;
    private double weight;
    private double length;
    private double bmi;
    private State breasts;
    private State vagina;
    private String examination;
    private ConsultationType consultationType;
    private Long gynecologySubRecordId;
    private Long fertilitySubRecordId;
    private Long obstetricsRecordId;
    private List<EchographieDTO> echographies;
}
