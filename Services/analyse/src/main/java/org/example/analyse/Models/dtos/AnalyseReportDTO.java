package org.example.analyse.Models.dtos;

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
