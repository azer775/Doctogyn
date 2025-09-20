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

    public  String toHtmlStructured() {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date
        html.append("<h3>Echographie - ").append(this.getDate() != null ? this.getDate() : "N/A").append("</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Report:</strong> ").append(this.getReport() != null ? this.getReport() : "N/A").append("</p>");
        html.append("<p><strong>Cycle Day:</strong> ").append(this.getCycleDay() != null ? this.getCycleDay() : "N/A").append("</p>");
        html.append("<p><strong>Condition:</strong> ").append(this.getCondition() != null ? this.getCondition() : "N/A").append("</p>");
        html.append("<br>");

        // Uterus Section
        html.append("<h4>Uterus</h4>");
        html.append("<p><strong>Uterus Size:</strong> ").append(this.getUterusSize() != null ? this.getUterusSize() : "N/A").append("</p>");
        html.append("<p><strong>Uterus Length (mm):</strong> ").append(this.getUterusLength()).append("</p>");
        html.append("<p><strong>Uterus Width (mm):</strong> ").append(this.getUterusWidth()).append("</p>");
        html.append("<p><strong>Myometre:</strong> ").append(this.getMyometre() != null ? this.getMyometre() : "N/A").append("</p>");
        html.append("<p><strong>Myomes Number:</strong> ").append(this.getMyomesNumber()).append("</p>");
        html.append("<p><strong>Endometrium Thickness (mm):</strong> ").append(this.getEndometriumThickness()).append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(this.getComment() != null ? this.getComment() : "N/A").append("</p>");
        html.append("<br>");

        // Right Ovary Section
        html.append("<h4>Right Ovary</h4>");
        html.append("<p><strong>Ovary:</strong> ").append(this.getOvaryR() != null ? this.getOvaryR() : "N/A").append("</p>");
        html.append("<p><strong>Ovary Size (mm):</strong> ").append(this.getOvaryRSize()).append("</p>");
        html.append("<p><strong>Cyst Size (mm):</strong> ").append(this.getCystSizeOR()).append("</p>");
        html.append("<p><strong>Diagnostic Presumptions:</strong> ")
                .append(this.getDiagnosticpresumptionsOR() != null && !this.getDiagnosticpresumptionsOR().isEmpty()
                        ? String.join(", ", this.getDiagnosticpresumptionsOR().toString())
                        : "N/A")
                .append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(this.getOvaryRComment() != null ? this.getOvaryRComment() : "N/A").append("</p>");
        html.append("<br>");

        // Left Ovary Section
        html.append("<h4>Left Ovary</h4>");
        html.append("<p><strong>Ovary:</strong> ").append(this.getOvaryL() != null ? this.getOvaryL() : "N/A").append("</p>");
        html.append("<p><strong>Ovary Size (mm):</strong> ").append(this.getOvaryLSize()).append("</p>");
        html.append("<p><strong>Cyst Size (mm):</strong> ").append(this.getCystSizeOL()).append("</p>");
        html.append("<p><strong>Diagnostic Presumptions:</strong> ")
                .append(this.getDiagnosticpresumptionsOL() != null && !this.getDiagnosticpresumptionsOL().isEmpty()
                        ? String.join(", ", this.getDiagnosticpresumptionsOL().toString())
                        : "N/A")
                .append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(this.getOvaryLComment() != null ? this.getOvaryLComment() : "N/A").append("</p>");
        html.append("<br>");

        // Right Pelvic Mass Section
        html.append("<h4>Right Pelvic Mass</h4>");
        html.append("<p><strong>Present:</strong> ").append(this.getPelvicMR() != null ? this.getPelvicMR() : "N/A").append("</p>");
        html.append("<p><strong>Size (mm):</strong> ").append(this.getPmRSize()).append("</p>");
        html.append("<p><strong>Diagnostic Presumptions:</strong> ")
                .append(this.getPelvicdiagnosticpresumptionsR() != null && !this.getPelvicdiagnosticpresumptionsR().isEmpty()
                        ? String.join(", ", this.getPelvicdiagnosticpresumptionsR().toString())
                        : "N/A")
                .append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(this.getPmRComment() != null ? this.getPmRComment() : "N/A").append("</p>");
        html.append("<br>");

        // Left Pelvic Mass Section
        html.append("<h4>Left Pelvic Mass</h4>");
        html.append("<p><strong>Present:</strong> ").append(this.getPelvicML() != null ? this.getPelvicML() : "N/A").append("</p>");
        html.append("<p><strong>Size (mm):</strong> ").append(this.getPmLSize()).append("</p>");
        html.append("<p><strong>Diagnostic Presumptions:</strong> ")
                .append(this.getPelvicdiagnosticpresumptionsL() != null && !this.getPelvicdiagnosticpresumptionsL().isEmpty()
                        ? String.join(", ", this.getPelvicdiagnosticpresumptionsL().toString())
                        : "N/A")
                .append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(this.getPmLComment() != null ? this.getPmLComment() : "N/A").append("</p>");
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
}
