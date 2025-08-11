package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EchographieDTO {
    private Long id;
    private LocalDate date;
    private String report;
    private String cycleDay;
    private String condition;
    // Uterus
    private UterusSize uterusSize;
    private long uterusLength;
    private long uterusWidth;
    private Myometre myometre;
    private long myomesNumber;
    private long endometriumThickness;
    private String comment;
    // Right Ovary
    private Ovary ovaryR;
    private long ovaryRSize;
    private double cystSizeOR;
    private List<Diagnosticpresumption> diagnosticpresumptionsOR;
    private String ovaryRComment;
    // Left Ovary
    private Ovary ovaryL;
    private long ovaryLSize;
    private double cystSizeOL;
    private List<Diagnosticpresumption> diagnosticpresumptionsOL;
    private String ovaryLComment;
    // Right Pelvic Mass
    private Boolean pelvicMR;
    private double pmRSize;
    private List<Pelvicdiagnosticpresumption> pelvicdiagnosticpresumptionsR;
    private String pmRComment;
    // Left Pelvic Mass
    private Boolean pelvicML;
    private double pmLSize;
    private List<Pelvicdiagnosticpresumption> pelvicdiagnosticpresumptionsL;
    private String pmLComment;
    private Long consultationId;
}
