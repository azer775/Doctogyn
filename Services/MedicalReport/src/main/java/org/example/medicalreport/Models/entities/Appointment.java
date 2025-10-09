package org.example.medicalreport.Models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.medicalreport.Models.enums.ConsultationType;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    LocalDateTime date;
    String reason;
    @Enumerated(EnumType.STRING)
    ConsultationType consultationType;
    long cabinetId;
    @ToString.Exclude
    @ManyToOne
    private MedicalRecord medicalRecord;

}
