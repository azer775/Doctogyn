package org.example.analyse.Controllers;

import org.example.analyse.Models.entities.SpermAnalysis;
import org.example.analyse.Services.SpermAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sperm-analyses")
public class SpermAnalysisController {

    @Autowired
    private SpermAnalysisService spermAnalysisService;

    @GetMapping
    public List<SpermAnalysis> getAll() {
        return spermAnalysisService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpermAnalysis> getById(@PathVariable Long id) {
        Optional<SpermAnalysis> spermAnalysis = spermAnalysisService.findById(id);
        return spermAnalysis.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SpermAnalysis create(@RequestBody SpermAnalysis spermAnalysis) {
        return spermAnalysisService.save(spermAnalysis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpermAnalysis> update(@PathVariable Long id, @RequestBody SpermAnalysis spermAnalysisDetails) {
        try {
            SpermAnalysis updatedSpermAnalysis = spermAnalysisService.update(id, spermAnalysisDetails);
            return ResponseEntity.ok(updatedSpermAnalysis);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        spermAnalysisService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<SpermAnalysis> getByConsultationId(@PathVariable Long consultationId) {
        return spermAnalysisService.findByConsultationId(consultationId);
    }
}
