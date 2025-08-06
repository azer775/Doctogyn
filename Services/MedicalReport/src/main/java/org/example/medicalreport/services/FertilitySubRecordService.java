package org.example.medicalreport.services;

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
}
