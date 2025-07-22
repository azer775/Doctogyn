package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.Size;
import org.example.medicalreport.Models.enums.UterusSize;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchographieDTO {
    private Long id;
    private Size size;
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
    private Long consultationId;
}
