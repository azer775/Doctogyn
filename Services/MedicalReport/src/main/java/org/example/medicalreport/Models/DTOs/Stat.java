package org.example.medicalreport.Models.DTOs;

import lombok.Data;
import org.example.medicalreport.Models.enums.ConsultationType;

import java.time.LocalDate;
@Data
public class Stat {
    private LocalDate date;
    private Integer echographyCount;
    private Integer doctorId;
    private ConsultationType consultationType;

    public Stat(LocalDate date, Integer echographyCount, Integer doctorId, ConsultationType consultationType) {
        this.date = date;
        this.echographyCount = echographyCount;
        this.doctorId = doctorId;
        this.consultationType = consultationType;
    }
}
