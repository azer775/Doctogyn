package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.HormoneStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GynecologySubRecordDTO {
    private Long id;
    private String work;
    private String civilState;
    private HormoneStatus hormoneStatus;
    private LocalDate menopause;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private int periodMax;
    private int periodMin;
    private LocalDate date;
    private LocalDate background;
    private List<ConsultationDTO> consultations;
    private Long medicalRecordId;

}
