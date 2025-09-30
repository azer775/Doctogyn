package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8020/auth", configuration = FeignConfig.class)
public interface User {
    @GetMapping("/current")
    int getCurrentUser(@RequestParam String token);
}
