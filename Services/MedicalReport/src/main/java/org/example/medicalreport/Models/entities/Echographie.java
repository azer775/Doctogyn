package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.UterusSize;
import org.example.medicalreport.Models.enums.Size;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Echographie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private Size  size;
    private LocalDate date;
    private String report;
    private String cycleDay;
    private String condition;
    private UterusSize uterusSize;
    private String endometrium;
    private String myometre;
    private String ROSize;
    private String ROComment;
    private String LOComment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultation")
    private Consultation consultation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getCycleDay() {
        return cycleDay;
    }

    public void setCycleDay(String cycleDay) {
        this.cycleDay = cycleDay;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public UterusSize getUterusSize() {
        return uterusSize;
    }

    public void setUterusSize(UterusSize uterusSize) {
        this.uterusSize = uterusSize;
    }

    public String getEndometrium() {
        return endometrium;
    }

    public void setEndometrium(String endometrium) {
        this.endometrium = endometrium;
    }

    public String getMyometre() {
        return myometre;
    }

    public void setMyometre(String myometre) {
        this.myometre = myometre;
    }

    public String getROSize() {
        return ROSize;
    }

    public void setROSize(String ROSize) {
        this.ROSize = ROSize;
    }

    public String getROComment() {
        return ROComment;
    }

    public void setROComment(String ROComment) {
        this.ROComment = ROComment;
    }

    public String getLOComment() {
        return LOComment;
    }

    public void setLOComment(String LOComment) {
        this.LOComment = LOComment;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
}
