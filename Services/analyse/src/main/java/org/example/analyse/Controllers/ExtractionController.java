package org.example.analyse.Controllers;

import org.example.analyse.Models.dtos.ExtractionResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/extraction")
public class ExtractionController {
    @PostMapping("test")
    public ExtractionResponse Extract(@RequestBody ExtractionResponse response) {
        System.out.println("Received ExtractionResponse: " + response);
        return response;
    }
}
