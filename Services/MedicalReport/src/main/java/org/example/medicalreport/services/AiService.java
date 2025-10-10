package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.example.medicalreport.Models.AlertDTOs.AlertRequest;
import org.example.medicalreport.Models.AlertDTOs.AlertResponse;
import org.example.medicalreport.Models.SummaryDTOs.FinalResponse;
import org.example.medicalreport.Models.SummaryDTOs.SummaryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-service", configuration = FeignConfig.class)
public interface AiService {
    @PostMapping("/pdf/to-markdown")
    FinalResponse toMarkdown(SummaryRequest input);
    @PostMapping("/pdf/testvector")
    AlertResponse alersts(@RequestBody AlertRequest request);
}
