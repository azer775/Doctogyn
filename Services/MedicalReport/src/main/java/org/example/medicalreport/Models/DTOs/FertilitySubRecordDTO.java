package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.Models.enums.CivilState;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FertilitySubRecordDTO {
    private Long id;
    private int age;
    private LocalDate infertility;
    private LocalDate date;
    private long duration;
    private int cycleLength;
    private int cycleMin;
    private int cycleMax;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private CivilState civilState;
    private String comment;
    private List<ConsultationDTO> consultations;
    private Long medicalRecordId;
}
