package org.example.medicalreport.Models.AlertDTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlertRequest {
    String resume;
    String consultation;
}
