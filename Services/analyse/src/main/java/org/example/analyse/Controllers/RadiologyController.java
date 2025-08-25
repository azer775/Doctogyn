package org.example.analyse.Controllers;

import org.example.analyse.Models.entities.Radiology;
import org.example.analyse.Services.RadiologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/radiologies")
public class RadiologyController {

    @Autowired
    private RadiologyService radiologyService;

    @GetMapping
    public List<Radiology> getAll() {
        return radiologyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Radiology> getById(@PathVariable Long id) {
        Optional<Radiology> radiology = radiologyService.findById(id);
        return radiology.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Radiology create(@RequestBody Radiology radiology) {
        return radiologyService.save(radiology);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Radiology> update(@PathVariable Long id, @RequestBody Radiology radiologyDetails) {
        try {
            Radiology updatedRadiology = radiologyService.update(id, radiologyDetails);
            return ResponseEntity.ok(updatedRadiology);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        radiologyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Radiology> getByConsultationId(@PathVariable Long consultationId) {
        return radiologyService.findByConsultationId(consultationId);
    }
}
