package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MedicalBackground {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private FamilialPathology familialPathology;
    private Allergies allergies;
    private MedicalPathology medicalPathology;
    private ChirurgicalPathology chirurgicalPathology;
    private String surgicalApproach;
    private BackgroundType backgroundType;
    private LocalDate date;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;


}
