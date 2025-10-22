package org.example.medicalreport.Models.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportRequestDTO {
    List<FertilitySubRecordDTO> fertilitySubRecords;
    List<GynecologySubRecordDTO> gynecologySubRecords;
    List<ObstetricsRecordDTO> obstetricsRecords;
    boolean familialBackground;
    boolean medicalBackground;
    boolean allergiesBackground;
    boolean chirurgicalBackground;
    boolean echography;
    AnalyseReportDTO analyseReport;


}
