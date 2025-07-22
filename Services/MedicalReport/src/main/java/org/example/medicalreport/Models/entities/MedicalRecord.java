package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.CivilState;
import org.example.medicalreport.Models.enums.Sex;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Sex sex;
    private CivilState civilState;
    private String email;
    private String comment;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalBackground> medicalBackgrounds = new ArrayList<>();

    @OneToMany(mappedBy = "medicalRecord", orphanRemoval = true)
    private List<FertilitySubRecord> fertilitySubRecords = new ArrayList<>();

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GynecologySubRecord> gynecologySubRecords = new ArrayList<>();

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObstetricsRecord> obstetricsRecords = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public CivilState getCivilState() {
        return civilState;
    }

    public void setCivilState(CivilState civilState) {
        this.civilState = civilState;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<MedicalBackground> getMedicalBackgrounds() {
        return medicalBackgrounds;
    }

    public void setMedicalBackgrounds(List<MedicalBackground> medicalBackgrounds) {
        this.medicalBackgrounds = medicalBackgrounds;
    }

    public List<FertilitySubRecord> getFertilitySubRecords() {
        return fertilitySubRecords;
    }

    public void setFertilitySubRecords(List<FertilitySubRecord> fertilitySubRecords) {
        this.fertilitySubRecords = fertilitySubRecords;
    }

    public List<GynecologySubRecord> getGynecologySubRecords() {
        return gynecologySubRecords;
    }

    public void setGynecologySubRecords(List<GynecologySubRecord> gynecologySubRecords) {
        this.gynecologySubRecords = gynecologySubRecords;
    }

    public List<ObstetricsRecord> getObstetricsRecords() {
        return obstetricsRecords;
    }

    public void setObstetricsRecords(List<ObstetricsRecord> obstetricsRecords) {
        this.obstetricsRecords = obstetricsRecords;
    }
}
