package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.services.FertilitySubRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("/fertility-sub-records")
public class FertilitySubRecordController {
    @Autowired
    private FertilitySubRecordService fertilitySubRecordService;

    @PostMapping("/add")
    public ResponseEntity<FertilitySubRecordDTO> createFertilitySubRecord(@RequestBody FertilitySubRecordDTO dto) {
        FertilitySubRecordDTO created = fertilitySubRecordService.createFertilitySubRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<FertilitySubRecordDTO> getFertilitySubRecord(@PathVariable Long id) {
        FertilitySubRecordDTO dto = fertilitySubRecordService.getSubRecordConsultationIdsAndDates(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<FertilitySubRecordDTO>> getAllFertilitySubRecords() {
        return ResponseEntity.ok(fertilitySubRecordService.getAllFertilitySubRecords());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FertilitySubRecordDTO> updateFertilitySubRecord(@PathVariable Long id, @RequestBody FertilitySubRecordDTO dto) {
        FertilitySubRecordDTO updated = fertilitySubRecordService.updateFertilitySubRecord(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFertilitySubRecord(@PathVariable Long id) {
        fertilitySubRecordService.deleteFertilitySubRecord(id);
        return ResponseEntity.noContent().build();
    }
}
