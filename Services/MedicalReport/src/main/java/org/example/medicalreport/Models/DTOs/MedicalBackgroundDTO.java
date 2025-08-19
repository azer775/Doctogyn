package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalBackgroundDTO {
    private Long id;
    private FamilialPathology familialPathology;
    private Allergies allergies;
    private MedicalPathology medicalPathology;
    private ChirurgicalPathology chirurgicalPathology;
    private String surgicalApproach;
    private String comment;
    private BackgroundType backgroundType;
    private Long medicalRecordId;
}
