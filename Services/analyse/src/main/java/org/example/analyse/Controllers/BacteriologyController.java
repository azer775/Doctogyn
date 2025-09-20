package org.example.analyse.Controllers;

import org.example.analyse.Models.dtos.Document;
import org.example.analyse.Models.entities.Bacteriology;
import org.example.analyse.Models.enums.BacteriologyInterpretation;
import org.example.analyse.Models.enums.BacteriologyType;
import org.example.analyse.Models.enums.Germ;
import org.example.analyse.Services.BacteriologyService;
import org.example.analyse.Services.FastApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    @GetMapping("/html")
    public String tohtml(){
        Bacteriology bacteriology = Bacteriology.builder()
                .id(1)
                .date(LocalDate.of(2025, 9, 18))
                .type(BacteriologyType.fromId(-1)) // Assuming BacteriologyType is an enum
                .germs(List.of(Germ.fromId(5),Germ.fromId(2))) // Assuming Germ is an enum
                .interpretation(BacteriologyInterpretation.POSITIVE) // Assuming BacteriologyInterpretation is an enum
                .comment("Requires treatment")
                .consultationId(100L)
                .build();

        String htmlRow = bacteriology.toHtmlRow();
        System.out.println(htmlRow);
        return htmlRow;
    }

}
