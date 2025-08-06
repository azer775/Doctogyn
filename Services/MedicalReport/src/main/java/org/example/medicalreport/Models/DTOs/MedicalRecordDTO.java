package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.enums.CivilState;
import org.example.medicalreport.Models.enums.Sex;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordDTO {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Sex sex;
    private CivilState civilState;
    private String email;
    private String comment;
    private List<MedicalBackgroundDTO> medicalBackgrounds;
    private List<GynecologySubRecordDTO> gynecologySubRecords;
    private List<FertilitySubRecordDTO> fertilitySubRecords;
    private List<ObstetricsRecordDTO> obstetricsRecords;
}
