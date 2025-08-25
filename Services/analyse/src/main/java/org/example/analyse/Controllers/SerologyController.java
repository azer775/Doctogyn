package org.example.analyse.Controllers;

import org.example.analyse.Models.entities.Serology;
import org.example.analyse.Services.SerologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/serologies")
public class SerologyController {

    @Autowired
    private SerologyService serologyService;

    @GetMapping
    public List<Serology> getAll() {
        return serologyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serology> getById(@PathVariable Long id) {
        Optional<Serology> serology = serologyService.findById(id);
        return serology.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Serology create(@RequestBody Serology serology) {
        return serologyService.save(serology);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serology> update(@PathVariable Long id, @RequestBody Serology serologyDetails) {
        try {
            Serology updatedSerology = serologyService.update(id, serologyDetails);
            return ResponseEntity.ok(updatedSerology);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serologyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Serology> getByConsultationId(@PathVariable Long consultationId) {
        return serologyService.findByConsultationId(consultationId);
    }
}
