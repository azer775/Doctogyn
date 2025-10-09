package org.example.medicalreport.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConsultationType;
import org.example.medicalreport.Models.enums.State;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
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
    private LocalDateTime updatedAt;
    @Column(columnDefinition="TEXT")
    private String prescription;

    @ManyToOne
    private GynecologySubRecord gynecologySubRecord;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Echographie> echographies = new ArrayList<>();

    @ManyToOne
    private FertilitySubRecord fertilitySubRecord;

    @ManyToOne
    private ObstetricsRecord obstetricsRecord;
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.updatedAt = LocalDateTime.now();
    }

}
