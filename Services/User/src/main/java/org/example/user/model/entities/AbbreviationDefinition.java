package org.example.user.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbbreviationDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String abbreviation;
    String meaning;
    @ManyToOne
    private Doctor doctor;

}
