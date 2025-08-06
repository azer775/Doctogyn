package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.EchographieDTO;
import org.example.medicalreport.services.EchographieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/echographies")
public class EchographieController {

    @Autowired
    private EchographieService echographieService;

    @PostMapping("/add")
    public ResponseEntity<EchographieDTO> createEchographie(@RequestBody EchographieDTO dto) {
        EchographieDTO created = echographieService.createEchographie(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<EchographieDTO> getEchographie(@PathVariable Long id) {
        EchographieDTO dto = echographieService.getEchographie(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<EchographieDTO>> getAllEchographies() {
        return ResponseEntity.ok(echographieService.getAllEchographies());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EchographieDTO> updateEchographie(@PathVariable Long id, @RequestBody EchographieDTO dto) {
        EchographieDTO updated = echographieService.updateEchographie(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEchographie(@PathVariable Long id) {
        echographieService.deleteEchographie(id);
        return ResponseEntity.noContent().build();
    }
}
