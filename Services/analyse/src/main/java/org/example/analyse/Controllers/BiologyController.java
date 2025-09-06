package org.example.analyse.Controllers;

import org.example.analyse.Models.entities.Biology;
import org.example.analyse.Services.BiologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/biologies")
public class BiologyController {

    @Autowired
    private BiologyService biologyService;

    @GetMapping("/all")
    public List<Biology> getAll() {
        return biologyService.findAll();
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<Biology> getById(@PathVariable Long id) {
        Optional<Biology> biology = biologyService.findById(id);
        return biology.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Biology create(@RequestBody Biology biology) {
        return biologyService.save(biology);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Biology> update(@PathVariable Long id, @RequestBody Biology biologyDetails) {
        try {
            Biology updatedBiology = biologyService.update(id, biologyDetails);
            return ResponseEntity.ok(updatedBiology);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        biologyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Biology> getByConsultationId(@PathVariable Long consultationId) {
        return biologyService.findByConsultationId(consultationId);
    }
}
