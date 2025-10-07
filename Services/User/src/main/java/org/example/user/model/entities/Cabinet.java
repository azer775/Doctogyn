package org.example.user.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String adress;
    int tel;
    double fertilityRate;
    double gynecologyRate;
    double obstetricsRate;
    double echographyRate;

}
