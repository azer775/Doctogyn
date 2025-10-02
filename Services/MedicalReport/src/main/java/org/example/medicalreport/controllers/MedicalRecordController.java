package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.MedicalRecordDTO;
import org.example.medicalreport.Models.SummaryDTOs.AbbreviationDefinition;
import org.example.medicalreport.Models.SummaryDTOs.FinalResponse;
import org.example.medicalreport.services.MedicalRecordService;
import org.example.medicalreport.services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;
    @Autowired
    private User userService;

    @PostMapping("/add")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO dto) {
        MedicalRecordDTO created = medicalRecordService.createMedicalRecord(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable Long id) {
        MedicalRecordDTO dto = medicalRecordService.getMedicalRecordWithSubRecordIdsAndDates(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("all")
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordDTO dto) {
        MedicalRecordDTO updated = medicalRecordService.updateMedicalRecord(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/tohtml/{id}")
    public ResponseEntity<String> getMedicalRecordAsHtml(@PathVariable Long id) {
        String htmlContent = medicalRecordService.toHtmlStructured(id);
        return htmlContent != null ? ResponseEntity.ok(htmlContent) : ResponseEntity.notFound().build();
    }
    @GetMapping("/getresume/{id}")
    public ResponseEntity<FinalResponse> getMedicalRecordSummary(@PathVariable Long id,@RequestHeader (value = "Authorization", required = false) String auth) {
        FinalResponse summary = medicalRecordService.getSummary(id, auth);
        return summary != null ? ResponseEntity.ok(summary) : ResponseEntity.notFound().build();
    }
    @PostMapping("getresabb/{id}")
    public ResponseEntity<FinalResponse> getMedicalRecordSummarywithabb(@PathVariable Long id,@RequestHeader (value = "Authorization", required = false) String auth,@RequestBody List<AbbreviationDefinition> definitions) {
        FinalResponse summary = medicalRecordService.getResume(id, auth, definitions);
        return summary != null ? ResponseEntity.ok(summary) : ResponseEntity.notFound().build();
    }
    @GetMapping("/testjwt")
    public ResponseEntity<Integer> testJwt(@RequestHeader (value = "Authorization", required = false) String auth) {
        System.out.println("Authorization header: " + auth);
        return ResponseEntity.ok(userService.getCurrentUser(auth.replace("Bearer ", "")));
    }
    @GetMapping("/abbreviations" )
    public ResponseEntity<AbbreviationDefinition> getAbbreviations(@RequestHeader (value = "Authorization", required = false) String auth) {
        System.out.println("Authorization header: " + auth);
        List<AbbreviationDefinition> defs = userService.getAbbreviationsByDoctor(auth);
        System.out.println(defs);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/addabbreviations" )
    public ResponseEntity<List<AbbreviationDefinition>> addAbbreviations(@RequestHeader (value = "Authorization", required = false) String auth, @RequestBody List<AbbreviationDefinition> definitions) {
        return userService.add(auth, definitions);
    }
}
