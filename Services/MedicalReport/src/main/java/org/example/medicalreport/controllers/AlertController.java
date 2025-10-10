package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.AlertDTOs.AlertRequest;
import org.example.medicalreport.Models.AlertDTOs.AlertResponse;
import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert")
@CrossOrigin("*")
public class AlertController {
    @Autowired
    AlertService alertService;

    @PostMapping("/create/{id}")
    public AlertResponse createAlert(@PathVariable Long id, @RequestBody ConsultationDTO consultation) {
        return alertService.getAlerts(id, consultation);
    }
}
