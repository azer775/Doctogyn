package org.example.medicalreport.services;

import org.example.medicalreport.Models.AlertDTOs.AlertRequest;
import org.example.medicalreport.Models.AlertDTOs.AlertResponse;
import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    @Autowired
    ConsultationService consultationService;
    @Autowired
    ResumeService resumeService;
    @Autowired
    AiService aiService;

    public AlertRequest createAlertRequest(Long medicalRecordId, ConsultationDTO consultationDTO) {
        String consultation = consultationService.toHtmlStructured(consultationDTO);
        String resume = resumeService.findByMedicalRecordId(medicalRecordId).getSummary();
        return AlertRequest.builder()
                .consultation(consultation)
                .resume(resume)
                .build();
    }
    public AlertResponse getAlerts(Long medicalRecordId, ConsultationDTO consultationDTO) {
        AlertRequest alertRequest = createAlertRequest(medicalRecordId, consultationDTO);

        return aiService.alersts(alertRequest);
    }


}
