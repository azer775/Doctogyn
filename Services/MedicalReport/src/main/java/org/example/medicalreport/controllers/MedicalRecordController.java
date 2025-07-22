package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.MedicalRecordDTO;
import org.example.medicalreport.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/add")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO dto) {
        MedicalRecordDTO created = medicalRecordService.createMedicalRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable Long id) {
        MedicalRecordDTO dto = medicalRecordService.getMedicalRecord(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordDTO dto) {
        MedicalRecordDTO updated = medicalRecordService.updateMedicalRecord(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
