package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.CivilState;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FertilitySubRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id; // Assuming an ID field for the entity
    private int age;
    private LocalDate infertility;
    private Duration duration;
    private int cycleLength;
    private int cycleMin;
    private int cycleMax;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private CivilState civilState;
    private String comment;

    @OneToMany(mappedBy = "fertilitySubRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    @ManyToOne
    private MedicalRecord medicalRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getInfertility() {
        return infertility;
    }

    public void setInfertility(LocalDate infertility) {
        this.infertility = infertility;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(int cycleLength) {
        this.cycleLength = cycleLength;
    }

    public int getCycleMin() {
        return cycleMin;
    }

    public void setCycleMin(int cycleMin) {
        this.cycleMin = cycleMin;
    }

    public int getCycleMax() {
        return cycleMax;
    }

    public void setCycleMax(int cycleMax) {
        this.cycleMax = cycleMax;
    }

    public Boolean getDysmenorrhea() {
        return dysmenorrhea;
    }

    public void setDysmenorrhea(Boolean dysmenorrhea) {
        this.dysmenorrhea = dysmenorrhea;
    }

    public Boolean getMenorrhagia() {
        return menorrhagia;
    }

    public void setMenorrhagia(Boolean menorrhagia) {
        this.menorrhagia = menorrhagia;
    }

    public Boolean getMetrorrhagia() {
        return metrorrhagia;
    }

    public void setMetrorrhagia(Boolean metrorrhagia) {
        this.metrorrhagia = metrorrhagia;
    }

    public CivilState getCivilState() {
        return civilState;
    }

    public void setCivilState(CivilState civilState) {
        this.civilState = civilState;
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
