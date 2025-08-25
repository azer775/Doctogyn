package org.example.analyse.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.analyse.Models.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
@Setter
public class Bacteriology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    BacteriologyType type;
    List<Germ> germs = new ArrayList<>();
    BacteriologyInterpretation interpretation;
    String comment;
    long consultationId;

}
