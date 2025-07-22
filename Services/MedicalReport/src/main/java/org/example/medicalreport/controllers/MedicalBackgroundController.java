package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.MedicalBackgroundDTO;
import org.example.medicalreport.services.MedicalBackgroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-backgrounds")
public class MedicalBackgroundController {
    @Autowired
    private MedicalBackgroundService medicalBackgroundService;

    @PostMapping("/add")
    public ResponseEntity<MedicalBackgroundDTO> createMedicalBackground(@RequestBody MedicalBackgroundDTO dto) {
        MedicalBackgroundDTO created = medicalBackgroundService.createMedicalBackground(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalBackgroundDTO> getMedicalBackground(@PathVariable Long id) {
        MedicalBackgroundDTO dto = medicalBackgroundService.getMedicalBackground(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<MedicalBackgroundDTO>> getAllMedicalBackgrounds() {
        return ResponseEntity.ok(medicalBackgroundService.getAllMedicalBackgrounds());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalBackgroundDTO> updateMedicalBackground(@PathVariable Long id, @RequestBody MedicalBackgroundDTO dto) {
        MedicalBackgroundDTO updated = medicalBackgroundService.updateMedicalBackground(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalBackground(@PathVariable Long id) {
        medicalBackgroundService.deleteMedicalBackground(id);
        return ResponseEntity.noContent().build();
    }
}
