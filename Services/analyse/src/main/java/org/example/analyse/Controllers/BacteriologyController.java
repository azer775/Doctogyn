package org.example.analyse.Controllers;

import org.example.analyse.Models.dtos.Document;
import org.example.analyse.Models.entities.Bacteriology;
import org.example.analyse.Services.BacteriologyService;
import org.example.analyse.Services.FastApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bacteriologies")
public class BacteriologyController {

    @Autowired
    private BacteriologyService bacteriologyService;
    @Autowired
    private FastApi fastApi;

    @GetMapping
    public List<Bacteriology> getAll() {
        return bacteriologyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bacteriology> getById(@PathVariable Long id) {
        Optional<Bacteriology> bacteriology = bacteriologyService.findById(id);
        return bacteriology.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Bacteriology create(@RequestBody Bacteriology bacteriology) {
        return bacteriologyService.save(bacteriology);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bacteriology> update(@PathVariable Long id, @RequestBody Bacteriology bacteriologyDetails) {
        try {
            Bacteriology updatedBacteriology = bacteriologyService.update(id, bacteriologyDetails);
            return ResponseEntity.ok(updatedBacteriology);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bacteriologyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Bacteriology> getByConsultationId(@PathVariable Long consultationId) {
        return bacteriologyService.findByConsultationId(consultationId);
    }
    /*@PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> testApi(@RequestParam("file") MultipartFile file) {
        List<MultipartFile> files = List.of(file);
        try {
            Object response = fastApi.uploadFile(files);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }
    @PostMapping("/test2")
    public ResponseEntity<String> testEndpoint(@RequestBody List<Document> documents) {
        return ResponseEntity.ok("Test endpoint is working!");
    }*/

}
