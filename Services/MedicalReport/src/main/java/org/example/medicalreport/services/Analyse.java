package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.example.medicalreport.Models.AnalyseDTOs.ExtractionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analyse-service", url = "http://localhost:8090", configuration = FeignConfig.class)
public interface Analyse {
    @PostMapping(value ="/extraction/test", consumes = "application/json")
    ExtractionResponse test(@RequestBody ExtractionResponse response);
}
