package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.*;

import java.time.LocalDate;
import java.util.List;

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
    /*  file summary  */
    private LocalDate date;
    private String report;
    private String cycleDay;
    private String condition;
    /*  uterus  */
    private UterusSize uterusSize;
    private long uterusLength;
    private long uterusWidth;
    private Myometre myometre;
    private long myomesNumber;
    private long endometriumThickness;
    private String comment;
    /* right ovary */
    private Ovary ovaryR;
    private long ovaryRSize;
    private double cystSizeOR;
    private List<Diagnosticpresumption> diagnosticpresumptionsOR;
    private String ovaryRComment;
    /* left ovary */
    private Ovary ovaryL;
    private long ovaryLSize;
    private double cystSizeOL;
    private List<Diagnosticpresumption> diagnosticpresumptionsOL;
    private String ovaryLComment;
    /* right Pelvic mass */
    private Boolean pelvicMR;
    private double pmRSize;
    private List<Pelvicdiagnosticpresumption> pelvicdiagnosticpresumptionsR;
    private String pmRComment;
    /* left Pelvic mass */
    private Boolean pelvicML;
    private double pmLSize;
    private List<Pelvicdiagnosticpresumption> pelvicdiagnosticpresumptionsL;
    private String pmLComment;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consultation")
    private Consultation consultation;

}
