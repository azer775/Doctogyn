package org.example.user.model.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationRequest {
    private String nom;
    private String prenom;
    private String email;
    private String pwd;
    private String idun;
    private LocalDate datenaiss;
}
