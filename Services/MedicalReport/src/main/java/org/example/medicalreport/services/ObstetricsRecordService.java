package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.Models.DTOs.ObstetricsRecordDTO;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.Models.entities.ObstetricsRecord;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.example.medicalreport.repositories.ObstetricsRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .conceptionDate(obstetricsRecord.getConceptionDate())
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
}
