package org.example.medicalreport.Models.DTOs;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.example.medicalreport.Models.enums.ConsultationType;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDTO {
    private Long id;
    LocalDateTime date;
    String reason;
    @Enumerated(EnumType.STRING)
    ConsultationType consultationType;
    long cabinetId;

    private MedicalRecordDTO medicalRecord;
}
