package org.example.user.model.dtos;

import lombok.Builder;
import lombok.Data;
import org.example.user.model.entities.Cabinet;
import org.example.user.model.enums.Role;

import java.time.LocalDate;

@Data
@Builder
public class Crew {
    private String nom;
    private String prenom;
    private String email;
    private String pwd;
    private LocalDate datenaiss;
    private Cabinet cabinet;
    private Role role;
}
