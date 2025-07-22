package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.Allergies;
import org.example.medicalreport.Models.enums.ChirurgicalPathology;
import org.example.medicalreport.Models.enums.FamilialPathology;
import org.example.medicalreport.Models.enums.MedicalPathology;
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

    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilialPathology getFamilialPathology() {
        return familialPathology;
    }

    public void setFamilialPathology(FamilialPathology familialPathology) {
        this.familialPathology = familialPathology;
    }

    public Allergies getAllergies() {
        return allergies;
    }

    public void setAllergies(Allergies allergies) {
        this.allergies = allergies;
    }

    public MedicalPathology getMedicalPathology() {
        return medicalPathology;
    }

    public void setMedicalPathology(MedicalPathology medicalPathology) {
        this.medicalPathology = medicalPathology;
    }

    public ChirurgicalPathology getChirurgicalPathology() {
        return chirurgicalPathology;
    }

    public void setChirurgicalPathology(ChirurgicalPathology chirurgicalPathology) {
        this.chirurgicalPathology = chirurgicalPathology;
    }

    public String getSurgicalApproach() {
        return surgicalApproach;
    }

    public void setSurgicalApproach(String surgicalApproach) {
        this.surgicalApproach = surgicalApproach;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
