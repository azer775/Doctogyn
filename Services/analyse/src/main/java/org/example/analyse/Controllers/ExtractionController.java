package org.example.analyse.Controllers;

import org.example.analyse.Models.dtos.ExtractionResponse;
import org.example.analyse.Services.ExtractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extraction")
@CrossOrigin("*")
public class ExtractionController {
    @Autowired
    ExtractionService extractionService;
    @PostMapping("/test/{id}")
    public ExtractionResponse Extract(@RequestBody ExtractionResponse response,@PathVariable int id) {
        System.out.println("Received ExtractionResponse: " + response);
        return extractionService.addanalyses(response,id);
    }
    @GetMapping("/byConsultation/{id}")
    public ExtractionResponse getByConsultation(@PathVariable long id) {
        return extractionService.getByConsultation(id);
    }
    @PutMapping("/update")
    public ExtractionResponse updateExtract(@RequestBody ExtractionResponse response) {
        System.out.println("Received ExtractionResponse for update: " + response);
        return extractionService.updateanalyses(response);
    }
    @GetMapping("/getHtml/{id}")
    public String getHtmlAnalyses(@PathVariable long id) {
        return extractionService.toHtml(id);
    }
    @PostMapping("/getHtmlDoc")
    public String getDocAnalyses(@RequestBody ExtractionResponse response) {
        return extractionService.toHtml(response);
    }
}
