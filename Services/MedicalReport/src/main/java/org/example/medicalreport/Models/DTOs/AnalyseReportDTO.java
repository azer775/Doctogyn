package org.example.medicalreport.Models.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnalyseReportDTO {
    boolean bacteriology;
    boolean serology;
    boolean bloodGroup;
    boolean spermAnalysis;
    boolean biology;
    boolean radiology;
}
