package org.example.medicalreport.services;

import org.example.medicalreport.Configurations.FeignConfig;
import org.example.medicalreport.Models.SummaryDTOs.AbbreviationDefinition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8020", configuration = FeignConfig.class)
public interface User {
    @GetMapping("/auth/current")
    int getCurrentUser(@RequestParam String token);
    @GetMapping("/abbreviations/getbydoctor")
    List<AbbreviationDefinition> getAbbreviationsByDoctor(@RequestHeader(value = "Authorization",required = false) String token);
    @PostMapping("/abbreviations/add")
    ResponseEntity<List<AbbreviationDefinition>> add(@RequestHeader(value = "Authorization",required = false) String token, List<AbbreviationDefinition> definitions);
    @PostMapping("/auth/getDoctorsByCabinet")
    List<Integer> getDoctorsByCabinet(@RequestHeader(value = "Authorization", required = false) String auth);

}
