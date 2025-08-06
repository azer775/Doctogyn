package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.MedicalRecordDTO;
import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.example.medicalreport.Models.entities.GynecologySubRecord;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.Models.entities.Sub;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private GynecologySubRecordService gynecologySubRecordService;
    @Autowired
    private FertilitySubRecordService fertilitySubRecordService;
    @Autowired
    private ObstetricsRecordService obstetricsRecordService;
    @Autowired
    private MedicalBackgroundService medicalBackgroundService;

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO dto) {
        MedicalRecord medicalRecord = mapToEntity(dto);
        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);
        return mapToDTO(saved);
    }

    public MedicalRecordDTO getMedicalRecord(Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if(medicalRecord.isPresent()){
            MedicalRecord med = medicalRecord.get();
            if(med.getObstetricsRecords()!=null){

            }

        }

        return medicalRecord.map(this::mapToDTO).orElse(null);
    }
    /*
    * Show Medical record by id
    *
    * */
    public MedicalRecordDTO getMedicalRecordWithSubRecordIdsAndDates(Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if (medicalRecord.isPresent()) {
            MedicalRecord med = medicalRecord.get();
            MedicalRecordDTO dto = mapToDTO(med);
            // Populate subrecord lists with only ID and date
            dto.setGynecologySubRecords(
                    med.getGynecologySubRecords()
                            .stream()
                            .map(gynecologySubRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setFertilitySubRecords(
                    med.getFertilitySubRecords()
                            .stream()
                            .map(fertilitySubRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setObstetricsRecords(
                    med.getObstetricsRecords()
                            .stream()
                            .map(obstetricsRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setMedicalBackgrounds(
                    med.getMedicalBackgrounds().stream()
                            .map(medicalBackgroundService::mapToDTO)
                            .toList()
            );
            return dto;
        }
        return null;
    }

    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO dto) {
        Optional<MedicalRecord> existing = medicalRecordRepository.findById(id);
        if (existing.isPresent()) {
            MedicalRecord medicalRecord = mapToEntity(dto);
            medicalRecord.setId(id); // Ensure the ID is preserved for update
            MedicalRecord updated = medicalRecordRepository.save(medicalRecord);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    private MedicalRecordDTO mapToDTO(MedicalRecord medicalRecord) {
        return MedicalRecordDTO.builder()
                .id(medicalRecord.getId())
                .name(medicalRecord.getName())
                .surname(medicalRecord.getSurname())
                .birthDate(medicalRecord.getBirthDate())
                .sex(medicalRecord.getSex())
                .civilState(medicalRecord.getCivilState())
                .email(medicalRecord.getEmail())
                .comment(medicalRecord.getComment())
                .build();
    }

    private MedicalRecord mapToEntity(MedicalRecordDTO dto) {
        return MedicalRecord.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .birthDate(dto.getBirthDate())
                .sex(dto.getSex())
                .civilState(dto.getCivilState())
                .email(dto.getEmail())
                .comment(dto.getComment())
                .build();
    }

}
