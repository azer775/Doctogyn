package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.GynecologySubRecordDTO;
import org.example.medicalreport.services.GynecologySubRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gynecology-sub-records")
public class GynecologySubRecordController {
/*    @Autowired
    private GynecologySubRecordService gynecologySubRecordService;

    @PostMapping
    public ResponseEntity<GynecologySubRecordDTO> createGynecologySubRecord(@RequestBody GynecologySubRecordDTO dto) {
        GynecologySubRecordDTO created = gynecologySubRecordService.createGynecologySubRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GynecologySubRecordDTO> getGynecologySubRecord(@PathVariable Long id) {
        GynecologySubRecordDTO dto = gynecologySubRecordService.getGynecologySubRecord(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<GynecologySubRecordDTO>> getAllGynecologySubRecords() {
        return ResponseEntity.ok(gynecologySubRecordService.getAllGynecologySubRecords());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GynecologySubRecordDTO> updateGynecologySubRecord(@PathVariable Long id, @RequestBody GynecologySubRecordDTO dto) {
        GynecologySubRecordDTO updated = gynecologySubRecordService.updateGynecologySubRecord(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGynecologySubRecord(@PathVariable Long id) {
        gynecologySubRecordService.deleteGynecologySubRecord(id);
        return ResponseEntity.noContent().build();
    }*/
}
