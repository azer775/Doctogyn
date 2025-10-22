package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.Models.DTOs.ObstetricsRecordDTO;
import org.example.medicalreport.Models.DTOs.ReportRequestDTO;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.Models.entities.ObstetricsRecord;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.example.medicalreport.repositories.ObstetricsRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObstetricsRecordService {
    @Autowired
    private ObstetricsRecordRepository obstetricsRecordRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private ConsultationService consultationService;

    public ObstetricsRecordDTO createObstetricsRecord(ObstetricsRecordDTO dto) {
        ObstetricsRecord obstetricsRecord = mapToEntity(dto);
        obstetricsRecord.setDate(LocalDate.now());
        ObstetricsRecord saved = obstetricsRecordRepository.save(obstetricsRecord);
        return mapToDTO(saved);
    }

    public ObstetricsRecordDTO getObstetricsRecord(Long id) {
        Optional<ObstetricsRecord> obstetricsRecord = obstetricsRecordRepository.findById(id);
        return obstetricsRecord.map(this::mapToDTO).orElse(null);
    }

    public List<ObstetricsRecordDTO> getAllObstetricsRecords() {
        return obstetricsRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ObstetricsRecordDTO updateObstetricsRecord(Long id, ObstetricsRecordDTO dto) {
        Optional<ObstetricsRecord> existing = obstetricsRecordRepository.findById(id);
        if (existing.isPresent()) {
            ObstetricsRecord obstetricsRecord = mapToEntity(dto);
            obstetricsRecord.setId(id); // Ensure the ID is preserved for update
            ObstetricsRecord updated = obstetricsRecordRepository.save(obstetricsRecord);
            return mapToDTO(updated);
        }
        return null;
    }
    public ObstetricsRecordDTO mapToDateAndId(ObstetricsRecord obstetricsRecord) {
        return ObstetricsRecordDTO.builder()
                .id(obstetricsRecord.getId())
                .conceptionDate(obstetricsRecord.getDate())
                .build();
    }

    public void deleteObstetricsRecord(Long id) {
        obstetricsRecordRepository.deleteById(id);
    }
    public ObstetricsRecordDTO getSubRecordConsultationIdsAndDates(long id){
        Optional<ObstetricsRecord> subRecord = obstetricsRecordRepository.findById(id);
        if(subRecord.isPresent()){
            ObstetricsRecord obstetricsRecord= subRecord.get();
            ObstetricsRecordDTO dto=this.mapToDTO(obstetricsRecord);
            dto.setConsultations(obstetricsRecord.getConsultations().stream().map(consultationService::mapToDateAndId).toList());
            return dto;
        }
        return null;
    }

    private ObstetricsRecordDTO mapToDTO(ObstetricsRecord obstetricsRecord) {
        return ObstetricsRecordDTO.builder()
                .id(obstetricsRecord.getId())
                .date(obstetricsRecord.getDate())
                .conceptionType(obstetricsRecord.getConceptionType())
                .conceptionDate(obstetricsRecord.getConceptionDate())
                .ddr(obstetricsRecord.getDdr())
                .nfoetus(obstetricsRecord.getNfoetus())
                .comment(obstetricsRecord.getComment())
                .medicalRecordId(obstetricsRecord.getMedicalRecord() != null ? obstetricsRecord.getMedicalRecord().getId() : null)
                .build();
    }
    public ObstetricsRecordDTO mapToCompleteDTO(ObstetricsRecord obstetricsRecord) {
        return ObstetricsRecordDTO.builder()
                .id(obstetricsRecord.getId())
                .date(obstetricsRecord.getDate())
                .conceptionType(obstetricsRecord.getConceptionType())
                .conceptionDate(obstetricsRecord.getConceptionDate())
                .ddr(obstetricsRecord.getDdr())
                .nfoetus(obstetricsRecord.getNfoetus())
                .comment(obstetricsRecord.getComment())
                .consultations(obstetricsRecord.getConsultations().stream().map(consultationService::mapToDTO).toList())
                .medicalRecordId(obstetricsRecord.getMedicalRecord() != null ? obstetricsRecord.getMedicalRecord().getId() : null)
                .build();
    }

    private ObstetricsRecord mapToEntity(ObstetricsRecordDTO dto) {
        ObstetricsRecord obstetricsRecord = ObstetricsRecord.builder()
                .conceptionType(dto.getConceptionType())
                .conceptionDate(dto.getConceptionDate())
                .date(dto.getDate())
                .ddr(dto.getDdr())
                .nfoetus(dto.getNfoetus())
                .comment(dto.getComment())
                .consultations(dto.getConsultations().stream().map(consultationService::mapToEntity).toList())
                .build();
        if (dto.getMedicalRecordId() != null) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId());
            medicalRecord.ifPresent(obstetricsRecord::setMedicalRecord);
        }
        return obstetricsRecord;
    }
    public String toHtmlStructured(ObstetricsRecordDTO obstetricsRecord) {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date
        html.append("<h3>Obstetrics Record - ")
                .append(obstetricsRecord.getDate() != null ? obstetricsRecord.getDate() : "N/A")
                .append("</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Conception Type:</strong> ").append(obstetricsRecord.getConceptionType() != null ? obstetricsRecord.getConceptionType() : "N/A").append("</p>");
        html.append("<p><strong>Conception Date:</strong> ").append(obstetricsRecord.getConceptionDate() != null ? obstetricsRecord.getConceptionDate() : "N/A").append("</p>");
        html.append("<p><strong>DDR (Last Menstrual Period):</strong> ").append(obstetricsRecord.getDdr() != null ? obstetricsRecord.getDdr() : "N/A").append("</p>");
        html.append("<p><strong>Number of Fetuses:</strong> ").append(obstetricsRecord.getNfoetus()).append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(obstetricsRecord.getComment() != null ? obstetricsRecord.getComment() : "N/A").append("</p>");
        html.append("<br>");

        // Consultations Section
        html.append("<h4>Consultations</h4>");
        if (obstetricsRecord.getConsultations() != null && !obstetricsRecord.getConsultations().isEmpty()) {
            for (ConsultationDTO consultation : obstetricsRecord.getConsultations()) {
                html.append(consultationService.toHtmlStructured(consultation));
            }
        } else {
            html.append("<p>No consultations available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
    public String toHtmlReport(long id, ReportRequestDTO reportRequestDTO) {
        ObstetricsRecordDTO obstetricsRecord = this.mapToCompleteDTO(this.obstetricsRecordRepository.findById(id).orElse(null));
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date
        html.append("<h3>Obstetrics Record - ")
                .append(obstetricsRecord.getDate() != null ? obstetricsRecord.getDate() : "N/A")
                .append("</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Conception Type:</strong> ").append(obstetricsRecord.getConceptionType() != null ? obstetricsRecord.getConceptionType() : "N/A").append("</p>");
        html.append("<p><strong>Conception Date:</strong> ").append(obstetricsRecord.getConceptionDate() != null ? obstetricsRecord.getConceptionDate() : "N/A").append("</p>");
        html.append("<p><strong>DDR (Last Menstrual Period):</strong> ").append(obstetricsRecord.getDdr() != null ? obstetricsRecord.getDdr() : "N/A").append("</p>");
        html.append("<p><strong>Number of Fetuses:</strong> ").append(obstetricsRecord.getNfoetus()).append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(obstetricsRecord.getComment() != null ? obstetricsRecord.getComment() : "N/A").append("</p>");
        html.append("<br>");

        // Consultations Section
        html.append("<h4>Consultations</h4>");
        if (obstetricsRecord.getConsultations() != null && !obstetricsRecord.getConsultations().isEmpty()) {
            for (ConsultationDTO consultation : obstetricsRecord.getConsultations()) {
                html.append(consultationService.toHtmlReport(consultation,reportRequestDTO));
            }
        } else {
            html.append("<p>No consultations available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
}
