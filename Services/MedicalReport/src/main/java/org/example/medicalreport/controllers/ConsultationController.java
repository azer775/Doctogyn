package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.services.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/consultations")
public class ConsultationController
{

    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/add")
    public ResponseEntity<ConsultationDTO> createConsultation(@RequestBody ConsultationDTO dto) {
        ConsultationDTO created = consultationService.createConsultation(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<ConsultationDTO> getConsultation(@PathVariable Long id) {
        ConsultationDTO dto = consultationService.getConsultation(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ConsultationDTO> updateConsultation(@PathVariable Long id, @RequestBody ConsultationDTO dto) {
        ConsultationDTO updated = consultationService.updateConsultation(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
}
