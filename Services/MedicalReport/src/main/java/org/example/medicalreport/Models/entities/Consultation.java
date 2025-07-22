package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConsultationType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private LocalDate date;
    private String signsNegates;
    private LocalDate weight;
    private double length;
    private double bmi;
    private double breasts;
    private String vagina;
    private ConsultationType consultationType;

    @ManyToOne
    private GynecologySubRecord gynecologySubRecord;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Echographie> echographies = new ArrayList<>();

    @ManyToOne
    private FertilitySubRecord fertilitySubRecord;

    @ManyToOne
    private ObstetricsRecord obstetricsRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSignsNegates() {
        return signsNegates;
    }

    public void setSignsNegates(String signsNegates) {
        this.signsNegates = signsNegates;
    }

    public LocalDate getWeight() {
        return weight;
    }

    public void setWeight(LocalDate weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public double getBreasts() {
        return breasts;
    }

    public void setBreasts(double breasts) {
        this.breasts = breasts;
    }

    public String getVagina() {
        return vagina;
    }

    public void setVagina(String vagina) {
        this.vagina = vagina;
    }

    public ConsultationType getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(ConsultationType consultationType) {
        this.consultationType = consultationType;
    }

    public GynecologySubRecord getGynecologySubRecord() {
        return gynecologySubRecord;
    }

    public void setGynecologySubRecord(GynecologySubRecord gynecologySubRecord) {
        this.gynecologySubRecord = gynecologySubRecord;
    }

    public List<Echographie> getEchographies() {
        return echographies;
    }

    public void setEchographies(List<Echographie> echographies) {
        this.echographies = echographies;
    }

    public FertilitySubRecord getFertilitySubRecord() {
        return fertilitySubRecord;
    }

    public void setFertilitySubRecord(FertilitySubRecord fertilitySubRecord) {
        this.fertilitySubRecord = fertilitySubRecord;
    }

    public ObstetricsRecord getObstetricsRecord() {
        return obstetricsRecord;
    }

    public void setObstetricsRecord(ObstetricsRecord obstetricsRecord) {
        this.obstetricsRecord = obstetricsRecord;
    }
}
