package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConceptionType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ObstetricsRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private ConceptionType conceptionType;
    private LocalDate conceptionDate;
    private LocalDate ddr;
    private LocalDate date;
    private int nfoetus;
    private String comment;

    @OneToMany(mappedBy = "obstetricsRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    @ManyToOne
    private MedicalRecord medicalRecord;

}
