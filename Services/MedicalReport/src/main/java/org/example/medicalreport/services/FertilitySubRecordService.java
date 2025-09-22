package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.repositories.FertilitySubRecordRepository;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FertilitySubRecordService {

    @Autowired
    private FertilitySubRecordRepository fertilitySubRecordRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private ConsultationService consultationService;

    public FertilitySubRecordDTO createFertilitySubRecord(FertilitySubRecordDTO dto) {
        FertilitySubRecord fertilitySubRecord = mapToEntity(dto);
        FertilitySubRecord saved = fertilitySubRecordRepository.save(fertilitySubRecord);
        return mapToDTO(saved);
    }

    public FertilitySubRecordDTO getFertilitySubRecord(Long id) {
        Optional<FertilitySubRecord> fertilitySubRecord = fertilitySubRecordRepository.findById(id);
        return fertilitySubRecord.map(this::mapToDTO).orElse(null);
    }

    public List<FertilitySubRecordDTO> getAllFertilitySubRecords() {
        return fertilitySubRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public FertilitySubRecordDTO updateFertilitySubRecord(Long id, FertilitySubRecordDTO dto) {
        Optional<FertilitySubRecord> existing = fertilitySubRecordRepository.findById(id);
        if (existing.isPresent()) {
            FertilitySubRecord fertilitySubRecord = mapToEntity(dto);
            fertilitySubRecord.setId(id); // Ensure the ID is preserved for update
            FertilitySubRecord updated = fertilitySubRecordRepository.save(fertilitySubRecord);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteFertilitySubRecord(Long id) {
        fertilitySubRecordRepository.deleteById(id);
    }
    public FertilitySubRecordDTO mapToDateAndId(FertilitySubRecord fertilitySubRecord) {
        return FertilitySubRecordDTO.builder()
                .id(fertilitySubRecord.getId())
                .date(fertilitySubRecord.getDate())
                .build();
    }
    public FertilitySubRecordDTO getSubRecordConsultationIdsAndDates(long id){
        Optional<FertilitySubRecord> subRecord = fertilitySubRecordRepository.findById(id);
        if(subRecord.isPresent()){
            FertilitySubRecord fertilitySubRecord= subRecord.get();
            FertilitySubRecordDTO dto=this.mapToDTO(fertilitySubRecord);
            dto.setConsultations(fertilitySubRecord.getConsultations().stream().map(consultationService::mapToDateAndId).toList());
            return dto;
        }
        return null;
    }

    public FertilitySubRecordDTO mapToCompleteDTO(FertilitySubRecord fertilitySubRecord) {
        return FertilitySubRecordDTO.builder()
                .id(fertilitySubRecord.getId())
                .age(fertilitySubRecord.getAge())
                .infertility(fertilitySubRecord.getInfertility())
                .date(fertilitySubRecord.getDate())
                .duration(fertilitySubRecord.getDuration())
                .cycleLength(fertilitySubRecord.getCycleLength())
                .cycleMin(fertilitySubRecord.getCycleMin())
                .cycleMax(fertilitySubRecord.getCycleMax())
                .dysmenorrhea(fertilitySubRecord.getDysmenorrhea())
                .menorrhagia(fertilitySubRecord.getMenorrhagia())
                .metrorrhagia(fertilitySubRecord.getMetrorrhagia())
                .civilState(fertilitySubRecord.getCivilState())
                .comment(fertilitySubRecord.getComment())
                .consultations(fertilitySubRecord.getConsultations().stream().map(consultationService::mapToDTO).toList())
                .medicalRecordId(fertilitySubRecord.getMedicalRecord() != null ? fertilitySubRecord.getMedicalRecord().getId() : null)
                .build();
    }

    private FertilitySubRecordDTO mapToDTO(FertilitySubRecord fertilitySubRecord) {
        return FertilitySubRecordDTO.builder()
                .id(fertilitySubRecord.getId())
                .age(fertilitySubRecord.getAge())
                .infertility(fertilitySubRecord.getInfertility())
                .date(fertilitySubRecord.getDate())
                .duration(fertilitySubRecord.getDuration())
                .cycleLength(fertilitySubRecord.getCycleLength())
                .cycleMin(fertilitySubRecord.getCycleMin())
                .cycleMax(fertilitySubRecord.getCycleMax())
                .dysmenorrhea(fertilitySubRecord.getDysmenorrhea())
                .menorrhagia(fertilitySubRecord.getMenorrhagia())
                .metrorrhagia(fertilitySubRecord.getMetrorrhagia())
                .civilState(fertilitySubRecord.getCivilState())
                .comment(fertilitySubRecord.getComment())
                .medicalRecordId(fertilitySubRecord.getMedicalRecord() != null ? fertilitySubRecord.getMedicalRecord().getId() : null)
                .build();
    }

    private FertilitySubRecord mapToEntity(FertilitySubRecordDTO dto) {
        FertilitySubRecord fertilitySubRecord = FertilitySubRecord.builder()
                .age(dto.getAge())
                .infertility(dto.getInfertility())
                .date(dto.getDate())
                .duration(dto.getDuration())
                .cycleLength(dto.getCycleLength())
                .cycleMin(dto.getCycleMin())
                .cycleMax(dto.getCycleMax())
                .dysmenorrhea(dto.getDysmenorrhea())
                .menorrhagia(dto.getMenorrhagia())
                .metrorrhagia(dto.getMetrorrhagia())
                .civilState(dto.getCivilState())
                .comment(dto.getComment())
                .build();
        if (dto.getMedicalRecordId() != null) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId());
            medicalRecord.ifPresent(fertilitySubRecord::setMedicalRecord);
        }
        return fertilitySubRecord;
    }
    public String toHtmlStructured(FertilitySubRecordDTO fertilityRecord) {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date
        html.append("<h3>Fertility Sub-Record - ")
                .append(fertilityRecord.getDate() != null ? fertilityRecord.getDate() : "N/A")
                .append("</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Age:</strong> ").append(fertilityRecord.getAge()).append("</p>");
        html.append("<p><strong>Infertility Start Date:</strong> ").append(fertilityRecord.getInfertility() != null ? fertilityRecord.getInfertility() : "N/A").append("</p>");
        html.append("<p><strong>Duration (months):</strong> ").append(fertilityRecord.getDuration()).append("</p>");
        html.append("<p><strong>Comment:</strong> ").append(fertilityRecord.getComment() != null ? fertilityRecord.getComment() : "N/A").append("</p>");
        html.append("<br>");

        // Menstrual History Section
        html.append("<h4>Menstrual History</h4>");
        html.append("<p><strong>Cycle Length (days):</strong> ").append(fertilityRecord.getCycleLength()).append("</p>");
        html.append("<p><strong>Cycle Minimum (days):</strong> ").append(fertilityRecord.getCycleMin()).append("</p>");
        html.append("<p><strong>Cycle Maximum (days):</strong> ").append(fertilityRecord.getCycleMax()).append("</p>");
        html.append("<p><strong>Dysmenorrhea:</strong> ").append(fertilityRecord.getDysmenorrhea() != null ? fertilityRecord.getDysmenorrhea() : "N/A").append("</p>");
        html.append("<p><strong>Menorrhagia:</strong> ").append(fertilityRecord.getMenorrhagia() != null ? fertilityRecord.getMenorrhagia() : "N/A").append("</p>");
        html.append("<p><strong>Metrorrhagia:</strong> ").append(fertilityRecord.getMetrorrhagia() != null ? fertilityRecord.getMetrorrhagia() : "N/A").append("</p>");
        html.append("<br>");

        // Civil Status Section
        html.append("<h4>Civil Status</h4>");
        html.append("<p><strong>Civil Status:</strong> ").append(fertilityRecord.getCivilState() != null ? fertilityRecord.getCivilState() : "N/A").append("</p>");
        html.append("<br>");

        // Consultations Section
        html.append("<h4>Consultations</h4>");
        if (fertilityRecord.getConsultations() != null && !fertilityRecord.getConsultations().isEmpty()) {
            for (ConsultationDTO consultation : fertilityRecord.getConsultations()) {
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
