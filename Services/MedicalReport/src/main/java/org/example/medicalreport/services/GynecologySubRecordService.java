package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.GynecologySubRecordDTO;
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
/*
    @Autowired
    private GynecologySubRecordRepository gynecologySubRecordRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

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

    private GynecologySubRecordDTO mapToDTO(GynecologySubRecord gynecologySubRecord) {
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
    }*/
}
