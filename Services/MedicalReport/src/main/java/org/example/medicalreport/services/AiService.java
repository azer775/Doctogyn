package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ai-service", url = "http://localhost:8090", configuration = FeignConfig.class)
public interface AiService {
}
