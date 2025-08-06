package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.CivilState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FertilitySubRecord extends Sub{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id; // Assuming an ID field for the entity
    private int age;
    private LocalDate infertility;
    private LocalDate date;
    private long duration;
    private int cycleLength;
    private int cycleMin;
    private int cycleMax;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private CivilState civilState;
    private String comment;

    @OneToMany(mappedBy = "fertilitySubRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    @ManyToOne
    private MedicalRecord medicalRecord;


}
