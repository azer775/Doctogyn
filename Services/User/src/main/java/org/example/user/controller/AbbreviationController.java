package org.example.user.controller;

import org.example.user.model.entities.AbbreviationDefinition;
import org.example.user.service.AbbreviationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abbreviations")
public class AbbreviationController {
    @Autowired
    private AbbreviationService abbreviationService;

    @PostMapping("/add")
    public ResponseEntity<List<AbbreviationDefinition>> addAbbreviations(@RequestBody List<AbbreviationDefinition> definitions, @RequestHeader(value = "Authorization",required = false) String token) {
        return ResponseEntity.ok(abbreviationService.addAbbreviationDefinitions(definitions, token.replace("Bearer ", "")));
        // Implementation for adding abbreviations
    }
    @GetMapping("/getbydoctor" )
    public ResponseEntity<List<AbbreviationDefinition>> getAbbreviationsByDoctor(@RequestHeader(value = "Authorization",required = false) String token) {
        return ResponseEntity.ok(abbreviationService.getAbbreviationDefinitions(token.replace("Bearer ", "")));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AbbreviationDefinition> deleteAbbreviation(@PathVariable Long id) {
        AbbreviationDefinition deletedDef = abbreviationService.delete(id);
        if (deletedDef != null) {
            return ResponseEntity.ok(deletedDef);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
