package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.ObstetricsRecordDTO;
import org.example.medicalreport.services.ObstetricsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/obstetrics-records")
public class ObstetricsRecordController {
    @Autowired
    private ObstetricsRecordService obstetricsRecordService;

    @PostMapping("/add")
    public ResponseEntity<ObstetricsRecordDTO> createObstetricsRecord(@RequestBody ObstetricsRecordDTO dto) {
        ObstetricsRecordDTO created = obstetricsRecordService.createObstetricsRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<ObstetricsRecordDTO> getObstetricsRecord(@PathVariable Long id) {
        ObstetricsRecordDTO dto = obstetricsRecordService.getSubRecordConsultationIdsAndDates(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<ObstetricsRecordDTO>> getAllObstetricsRecords() {
        return ResponseEntity.ok(obstetricsRecordService.getAllObstetricsRecords());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ObstetricsRecordDTO> updateObstetricsRecord(@PathVariable Long id, @RequestBody ObstetricsRecordDTO dto) {
        ObstetricsRecordDTO updated = obstetricsRecordService.updateObstetricsRecord(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObstetricsRecord(@PathVariable Long id) {
        obstetricsRecordService.deleteObstetricsRecord(id);
        return ResponseEntity.noContent().build();
    }
}
