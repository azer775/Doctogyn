package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.Models.DTOs.GynecologySubRecordDTO;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.GynecologySubRecord;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.repositories.GynecologySubRecordRepository;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GynecologySubRecordService {

    @Autowired
    private GynecologySubRecordRepository gynecologySubRecordRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private ConsultationService consultationService;

    public GynecologySubRecordDTO createGynecologySubRecord(GynecologySubRecordDTO dto) {
        GynecologySubRecord gynecologySubRecord = mapToEntity(dto);
        GynecologySubRecord saved = gynecologySubRecordRepository.save(gynecologySubRecord);
        return mapToDTO(saved);
    }

    public GynecologySubRecordDTO getGynecologySubRecord(Long id) {
        Optional<GynecologySubRecord> gynecologySubRecord = gynecologySubRecordRepository.findById(id);
        return gynecologySubRecord.map(this::mapToDTO).orElse(null);
    }

    public List<GynecologySubRecordDTO> getAllGynecologySubRecords() {
        return gynecologySubRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public GynecologySubRecordDTO updateGynecologySubRecord(Long id, GynecologySubRecordDTO dto) {
        Optional<GynecologySubRecord> existing = gynecologySubRecordRepository.findById(id);
        if (existing.isPresent()) {
            GynecologySubRecord gynecologySubRecord = mapToEntity(dto);
            gynecologySubRecord.setId(id); // Ensure the ID is preserved for update
            GynecologySubRecord updated = gynecologySubRecordRepository.save(gynecologySubRecord);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteGynecologySubRecord(Long id) {
        gynecologySubRecordRepository.deleteById(id);
    }

    public GynecologySubRecordDTO mapToDTO(GynecologySubRecord gynecologySubRecord) {
        return GynecologySubRecordDTO.builder()
                .id(gynecologySubRecord.getId())
                .work(gynecologySubRecord.getWork())
                .civilState(gynecologySubRecord.getCivilState())
                .hormoneStatus(gynecologySubRecord.getHormoneStatus())
                .menopause(gynecologySubRecord.getMenopause())
                .dysmenorrhea(gynecologySubRecord.getDysmenorrhea())
                .menorrhagia(gynecologySubRecord.getMenorrhagia())
                .metrorrhagia(gynecologySubRecord.getMetrorrhagia())
                .periodMax(gynecologySubRecord.getPeriodMax())
                .periodMin(gynecologySubRecord.getPeriodMin())
                .date(gynecologySubRecord.getDate())
                .background(gynecologySubRecord.getBackground())
                .medicalRecordId(gynecologySubRecord.getMedicalRecord() != null ? gynecologySubRecord.getMedicalRecord().getId() : null)
                .build();
    }
    public GynecologySubRecordDTO mapToCompleteDTO(GynecologySubRecord gynecologySubRecord) {
        return GynecologySubRecordDTO.builder()
                .id(gynecologySubRecord.getId())
                .work(gynecologySubRecord.getWork())
                .civilState(gynecologySubRecord.getCivilState())
                .hormoneStatus(gynecologySubRecord.getHormoneStatus())
                .menopause(gynecologySubRecord.getMenopause())
                .dysmenorrhea(gynecologySubRecord.getDysmenorrhea())
                .menorrhagia(gynecologySubRecord.getMenorrhagia())
                .metrorrhagia(gynecologySubRecord.getMetrorrhagia())
                .periodMax(gynecologySubRecord.getPeriodMax())
                .periodMin(gynecologySubRecord.getPeriodMin())
                .date(gynecologySubRecord.getDate())
                .consultations(gynecologySubRecord.getConsultations().stream().map(consultationService::mapToDTO).toList())
                .background(gynecologySubRecord.getBackground())
                .medicalRecordId(gynecologySubRecord.getMedicalRecord() != null ? gynecologySubRecord.getMedicalRecord().getId() : null)
                .build();
    }
    public GynecologySubRecordDTO getSubRecordConsultationIdsAndDates(long id){
        Optional<GynecologySubRecord> subRecord = gynecologySubRecordRepository.findById(id);
        if(subRecord.isPresent()){
            GynecologySubRecord gynecologySubRecord= subRecord.get();
            GynecologySubRecordDTO dto=this.mapToDTO(gynecologySubRecord);
            dto.setConsultations(gynecologySubRecord.getConsultations().stream().map(consultationService::mapToDateAndId).toList());
            return dto;
        }
        return null;
    }
    public GynecologySubRecordDTO mapToDateAndId(GynecologySubRecord gynecologySubRecord){
        return GynecologySubRecordDTO.builder()
                .id(gynecologySubRecord.getId())
                .date(gynecologySubRecord.getDate())
                .build();
    }

    private GynecologySubRecord mapToEntity(GynecologySubRecordDTO dto) {
        GynecologySubRecord gynecologySubRecord = GynecologySubRecord.builder()
                .work(dto.getWork())
                .civilState(dto.getCivilState())
                .hormoneStatus(dto.getHormoneStatus())
                .menopause(dto.getMenopause())
                .dysmenorrhea(dto.getDysmenorrhea())
                .menorrhagia(dto.getMenorrhagia())
                .metrorrhagia(dto.getMetrorrhagia())
                .periodMax(dto.getPeriodMax())
                .periodMin(dto.getPeriodMin())
                .date(dto.getDate())
                .background(dto.getBackground())
                .build();
        if (dto.getMedicalRecordId() != null) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId());
            medicalRecord.ifPresent(gynecologySubRecord::setMedicalRecord);
        }
        return gynecologySubRecord;
    }
    public String toHtmlStructured(GynecologySubRecordDTO gynecologyRecord) {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date
        html.append("<h3>Gynecology Sub-Record - ")
                .append(gynecologyRecord.getDate() != null ? gynecologyRecord.getDate() : "N/A")
                .append("</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Work:</strong> ").append(gynecologyRecord.getWork() != null ? gynecologyRecord.getWork() : "N/A").append("</p>");
        html.append("<p><strong>Civil Status:</strong> ").append(!gynecologyRecord.getCivilState().isEmpty() ? gynecologyRecord.getCivilState() : "N/A").append("</p>");
        html.append("<p><strong>Hormone Status:</strong> ").append(gynecologyRecord.getHormoneStatus() != null ? gynecologyRecord.getHormoneStatus() : "N/A").append("</p>");
        html.append("<p><strong>Menopause Date:</strong> ").append(gynecologyRecord.getMenopause() != null ? gynecologyRecord.getMenopause() : "N/A").append("</p>");
        html.append("<p><strong>Background:</strong> ").append(gynecologyRecord.getBackground() != null ? gynecologyRecord.getBackground() : "N/A").append("</p>");
        html.append("<br>");

        // Menstrual History Section
        html.append("<h4>Menstrual History</h4>");
        html.append("<p><strong>Dysmenorrhea:</strong> ").append(gynecologyRecord.getDysmenorrhea() != null ? gynecologyRecord.getDysmenorrhea() : "N/A").append("</p>");
        html.append("<p><strong>Menorrhagia:</strong> ").append(gynecologyRecord.getMenorrhagia() != null ? gynecologyRecord.getMenorrhagia() : "N/A").append("</p>");
        html.append("<p><strong>Metrorrhagia:</strong> ").append(gynecologyRecord.getMetrorrhagia() != null ? gynecologyRecord.getMetrorrhagia() : "N/A").append("</p>");
        html.append("<p><strong>Period Minimum (days):</strong> ").append(gynecologyRecord.getPeriodMin()).append("</p>");
        html.append("<p><strong>Period Maximum (days):</strong> ").append(gynecologyRecord.getPeriodMax()).append("</p>");
        html.append("<br>");

        // Consultations Section
        html.append("<h4>Consultations</h4>");
        if (gynecologyRecord.getConsultations() != null && !gynecologyRecord.getConsultations().isEmpty()) {
            for (ConsultationDTO consultation : gynecologyRecord.getConsultations()) {
                html.append(consultationService.toHtmlStructured(consultation));
            }
        } else {
            html.append("<p>No consultations available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
}
