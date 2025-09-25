package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.example.medicalreport.Models.SummaryDTOs.FinalResponse;
import org.example.medicalreport.Models.SummaryDTOs.SummaryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ai-service", url = "http://localhost:8000/pdf", configuration = FeignConfig.class)
public interface AiService {
    @PostMapping("/to-markdown")
    FinalResponse toMarkdown(SummaryRequest input);
}
