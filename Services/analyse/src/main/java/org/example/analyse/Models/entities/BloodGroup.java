package org.example.analyse.Models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.analyse.Models.enums.BloodType;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BloodGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    BloodType type;
    String comment;
    long consultationId;
}
