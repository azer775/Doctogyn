package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConsultationType;
import org.example.medicalreport.Models.enums.State;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Assuming an ID field for the entity
    private LocalDate date;
    private String signsNegates;
    private double weight;
    private double length;
    private double bmi;
    private State breasts;
    private State vagina;
    @Column(columnDefinition="TEXT")
    private String examination;
    private ConsultationType consultationType;

    @ManyToOne
    private GynecologySubRecord gynecologySubRecord;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Echographie> echographies = new ArrayList<>();

    @ManyToOne
    private FertilitySubRecord fertilitySubRecord;

    @ManyToOne
    private ObstetricsRecord obstetricsRecord;


}
