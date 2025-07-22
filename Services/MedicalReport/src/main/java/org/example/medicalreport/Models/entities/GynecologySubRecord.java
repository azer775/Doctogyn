package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.HormoneStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GynecologySubRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
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

    @OneToMany(mappedBy = "gynecologySubRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    @ManyToOne
    private MedicalRecord medicalRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getCivilState() {
        return civilState;
    }

    public void setCivilState(String civilState) {
        this.civilState = civilState;
    }

    public HormoneStatus getHormoneStatus() {
        return hormoneStatus;
    }

    public void setHormoneStatus(HormoneStatus hormoneStatus) {
        this.hormoneStatus = hormoneStatus;
    }

    public LocalDate getMenopause() {
        return menopause;
    }

    public void setMenopause(LocalDate menopause) {
        this.menopause = menopause;
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

    public int getPeriodMax() {
        return periodMax;
    }

    public void setPeriodMax(int periodMax) {
        this.periodMax = periodMax;
    }

    public int getPeriodMin() {
        return periodMin;
    }

    public void setPeriodMin(int periodMin) {
        this.periodMin = periodMin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getBackground() {
        return background;
    }

    public void setBackground(LocalDate background) {
        this.background = background;
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
