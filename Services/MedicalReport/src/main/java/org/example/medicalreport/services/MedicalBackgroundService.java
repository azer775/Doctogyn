package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.MedicalBackgroundDTO;
import org.example.medicalreport.Models.entities.MedicalBackground;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.repositories.MedicalBackgroundRepository;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalBackgroundService {
    @Autowired
    private MedicalBackgroundRepository medicalBackgroundRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalBackgroundDTO createMedicalBackground(MedicalBackgroundDTO dto) {
        MedicalBackground medicalBackground = mapToEntity(dto);
        MedicalBackground saved = medicalBackgroundRepository.save(medicalBackground);
        return mapToDTO(saved);
    }

    public MedicalBackgroundDTO getMedicalBackground(Long id) {
        Optional<MedicalBackground> medicalBackground = medicalBackgroundRepository.findById(id);
        return medicalBackground.map(this::mapToDTO).orElse(null);
    }

    public List<MedicalBackgroundDTO> getAllMedicalBackgrounds() {
        return medicalBackgroundRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public MedicalBackgroundDTO updateMedicalBackground(Long id, MedicalBackgroundDTO dto) {
        Optional<MedicalBackground> existing = medicalBackgroundRepository.findById(id);
        if (existing.isPresent()) {
            MedicalBackground medicalBackground = mapToEntity(dto);
            medicalBackground.setId(id); // Ensure the ID is preserved for update
            MedicalBackground updated = medicalBackgroundRepository.save(medicalBackground);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteMedicalBackground(Long id) {
        medicalBackgroundRepository.deleteById(id);
    }

    public MedicalBackgroundDTO mapToDTO(MedicalBackground medicalBackground) {
        return MedicalBackgroundDTO.builder()
                .id(medicalBackground.getId())
                .familialPathology(medicalBackground.getFamilialPathology())
                .allergies(medicalBackground.getAllergies())
                .medicalPathology(medicalBackground.getMedicalPathology())
                .chirurgicalPathology(medicalBackground.getChirurgicalPathology())
                .surgicalApproach(medicalBackground.getSurgicalApproach())
                .backgroundType(medicalBackground.getBackgroundType())
                .medicalRecordId(medicalBackground.getMedicalRecord() != null ? medicalBackground.getMedicalRecord().getId() : null)
                .build();
    }

    public MedicalBackground mapToEntity(MedicalBackgroundDTO dto) {
        MedicalBackground medicalBackground = MedicalBackground.builder()
                .familialPathology(dto.getFamilialPathology())
                .allergies(dto.getAllergies())
                .medicalPathology(dto.getMedicalPathology())
                .chirurgicalPathology(dto.getChirurgicalPathology())
                .surgicalApproach(dto.getSurgicalApproach())
                .backgroundType(dto.getBackgroundType())
                .build();
        if (dto.getMedicalRecordId() != null) {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(dto.getMedicalRecordId());
            medicalRecord.ifPresent(medicalBackground::setMedicalRecord);
        }
        return medicalBackground;
    }


    public List<MedicalBackgroundDTO> addMBList(List<MedicalBackgroundDTO> mbList) {
        List<MedicalBackground> list = mbList.stream().map(this::mapToEntity).toList();
        return medicalBackgroundRepository.saveAll(list).stream().map(this::mapToDTO).toList();
    }
}
