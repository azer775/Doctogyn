package org.example.analyse.Controllers;

import org.example.analyse.Models.dtos.ExtractionResponse;
import org.example.analyse.Services.ExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extraction")
public class ExtractionController {
    @Autowired
    ExtractionService extractionService;
    @PostMapping("test")
    public ExtractionResponse Extract(@RequestBody ExtractionResponse response) {
        System.out.println("Received ExtractionResponse: " + response);
        return extractionService.test(response);
    }
    @GetMapping("/byConsultation/{id}")
    public ExtractionResponse getByConsultation(@PathVariable long id) {
        return extractionService.getByConsultation(id);
    }
}
