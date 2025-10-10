package org.example.medicalreport.Models.AlertDTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlertResponse {
    List<String> alerts;
}
