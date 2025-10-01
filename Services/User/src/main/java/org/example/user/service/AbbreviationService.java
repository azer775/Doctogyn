package org.example.user.service;

import org.example.user.model.entities.AbbreviationDefinition;
import org.example.user.model.entities.Doctor;
import org.example.user.repository.AbbreviationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbbreviationService {
    @Autowired
    private AbbreviationRepository abbreviationRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public List<AbbreviationDefinition> addAbbreviationDefinitions(List<AbbreviationDefinition> definitions,String token){
        Doctor doctor = authenticationService.getCurrentUser(token);
        for (AbbreviationDefinition def : definitions) {
            def.setDoctor(doctor);
        }
        return abbreviationRepository.saveAll(definitions);
    }
    public List<AbbreviationDefinition> getAbbreviationDefinitions(String token){
        Doctor doctor = authenticationService.getCurrentUser(token);
        return abbreviationRepository.findByDoctorId(doctor.getId());
    }
    public AbbreviationDefinition delete(Long id) {
        AbbreviationDefinition def = abbreviationRepository.findById(id).orElse(null);
        if (def != null) {
            abbreviationRepository.delete(def);
        }
        return def;
    }
}
