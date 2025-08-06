package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.ConceptionType;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObstetricsRecordDTO {
    private Long id;
    private ConceptionType conceptionType;
    private LocalDate conceptionDate;
    private LocalDate ddr;
    private LocalDate date;
    private int nfoetus;
    private String comment;
    private List<ConsultationDTO> consultations;
    private Long medicalRecordId;
}
