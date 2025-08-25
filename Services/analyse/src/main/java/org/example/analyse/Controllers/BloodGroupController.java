package org.example.analyse.Controllers;

import org.example.analyse.Models.entities.BloodGroup;
import org.example.analyse.Services.BloodGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blood-groups")
public class BloodGroupController {

    @Autowired
    private BloodGroupService bloodGroupService;

    @GetMapping
    public List<BloodGroup> getAll() {
        return bloodGroupService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodGroup> getById(@PathVariable Long id) {
        Optional<BloodGroup> bloodGroup = bloodGroupService.findById(id);
        return bloodGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public BloodGroup create(@RequestBody BloodGroup bloodGroup) {
        return bloodGroupService.save(bloodGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodGroup> update(@PathVariable Long id, @RequestBody BloodGroup bloodGroupDetails) {
        try {
            BloodGroup updatedBloodGroup = bloodGroupService.update(id, bloodGroupDetails);
            return ResponseEntity.ok(updatedBloodGroup);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bloodGroupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultation/{consultationId}")
    public List<BloodGroup> getByConsultationId(@PathVariable Long consultationId) {
        return bloodGroupService.findByConsultationId(consultationId);
    }
}
