package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.Models.entities.Consultation;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.GynecologySubRecord;
import org.example.medicalreport.Models.entities.ObstetricsRecord;
import org.example.medicalreport.repositories.ConsultationRepository;
import org.example.medicalreport.repositories.FertilitySubRecordRepository;
import org.example.medicalreport.repositories.GynecologySubRecordRepository;
import org.example.medicalreport.repositories.ObstetricsRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private GynecologySubRecordRepository gynecologySubRecordRepository;

    @Autowired
    private FertilitySubRecordRepository fertilitySubRecordRepository;

    @Autowired
    private ObstetricsRecordRepository obstetricsRecordRepository;

    public ConsultationDTO createConsultation(ConsultationDTO dto) {
        Consultation consultation = mapToEntity(dto);
        Consultation saved = consultationRepository.save(consultation);
        return mapToDTO(saved);
    }

    public ConsultationDTO getConsultation(Long id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.map(this::mapToDTO).orElse(null);
    }

    public List<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ConsultationDTO updateConsultation(Long id, ConsultationDTO dto) {
        Optional<Consultation> existing = consultationRepository.findById(id);
        if (existing.isPresent()) {
            Consultation consultation = mapToEntity(dto);
            consultation.setId(id); // Ensure the ID is preserved for update
            Consultation updated = consultationRepository.save(consultation);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteConsultation(Long id) {
        consultationRepository.deleteById(id);
    }
    public ConsultationDTO mapToDateAndId(Consultation consultation) {
        return ConsultationDTO.builder()
                .id(consultation.getId())
                .date(consultation.getDate())
                .build();
    }

    public ConsultationDTO mapToDTO(Consultation consultation) {
        return ConsultationDTO.builder()
                .id(consultation.getId())
                .date(consultation.getDate())
                .signsNegates(consultation.getSignsNegates())
                .weight(consultation.getWeight())
                .length(consultation.getLength())
                .bmi(consultation.getBmi())
                .breasts(consultation.getBreasts())
                .vagina(consultation.getVagina())
                .examination(consultation.getExamination())
                .consultationType(consultation.getConsultationType())
                .gynecologySubRecordId(consultation.getGynecologySubRecord() != null ? consultation.getGynecologySubRecord().getId() : null)
                .fertilitySubRecordId(consultation.getFertilitySubRecord() != null ? consultation.getFertilitySubRecord().getId() : null)
                .obstetricsRecordId(consultation.getObstetricsRecord() != null ? consultation.getObstetricsRecord().getId() : null)
                .build();
    }

    public Consultation mapToEntity(ConsultationDTO dto) {
        Consultation consultation = Consultation.builder()
                .date(dto.getDate())
                .signsNegates(dto.getSignsNegates())
                .weight(dto.getWeight())
                .length(dto.getLength())
                .bmi(dto.getBmi())
                .breasts(dto.getBreasts())
                .vagina(dto.getVagina())
                .examination(dto.getExamination())
                .consultationType(dto.getConsultationType())
                .build();
        if (dto.getGynecologySubRecordId() != null) {
            Optional<GynecologySubRecord> gynecologySubRecord = gynecologySubRecordRepository.findById(dto.getGynecologySubRecordId());
            gynecologySubRecord.ifPresent(consultation::setGynecologySubRecord);
        }
        if (dto.getFertilitySubRecordId() != null) {
            Optional<FertilitySubRecord> fertilitySubRecord = fertilitySubRecordRepository.findById(dto.getFertilitySubRecordId());
            fertilitySubRecord.ifPresent(consultation::setFertilitySubRecord);
        }
        if (dto.getObstetricsRecordId() != null) {
            Optional<ObstetricsRecord> obstetricsRecord = obstetricsRecordRepository.findById(dto.getObstetricsRecordId());
            obstetricsRecord.ifPresent(consultation::setObstetricsRecord);
        }
        return consultation;
    }

}
