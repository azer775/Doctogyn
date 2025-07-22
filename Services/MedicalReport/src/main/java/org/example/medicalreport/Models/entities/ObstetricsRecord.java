package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConceptionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ObstetricsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private ConceptionType conceptionType;
    private LocalDate conceptionDate;
    private LocalDate ddr;
    private int nfoetus;
    private String comment;

    @OneToMany(mappedBy = "obstetricsRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    @ManyToOne
    private MedicalRecord medicalRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConceptionType getConceptionType() {
        return conceptionType;
    }

    public void setConceptionType(ConceptionType conceptionType) {
        this.conceptionType = conceptionType;
    }

    public LocalDate getConceptionDate() {
        return conceptionDate;
    }

    public void setConceptionDate(LocalDate conceptionDate) {
        this.conceptionDate = conceptionDate;
    }

    public LocalDate getDdr() {
        return ddr;
    }

    public void setDdr(LocalDate ddr) {
        this.ddr = ddr;
    }

    public int getNfoetus() {
        return nfoetus;
    }

    public void setNfoetus(int nfoetus) {
        this.nfoetus = nfoetus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
