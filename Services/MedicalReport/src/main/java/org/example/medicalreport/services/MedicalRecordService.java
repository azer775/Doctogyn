package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.FertilitySubRecordDTO;
import org.example.medicalreport.Models.DTOs.GynecologySubRecordDTO;
import org.example.medicalreport.Models.DTOs.MedicalRecordDTO;
import org.example.medicalreport.Models.DTOs.ObstetricsRecordDTO;
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
        if (medicalRecord.isPresent()) {
            MedicalRecord med = medicalRecord.get();
            MedicalRecordDTO dto = mapToDTO(med);
            // Populate subrecord lists with only ID and date
            dto.setGynecologySubRecords(
                    med.getGynecologySubRecords()
                            .stream()
                            .map(gynecologySubRecordService::mapToCompleteDTO)
                            .toList()
            );
            System.out.println("Gynecology SubRecords: " + dto.getGynecologySubRecords());

            dto.setFertilitySubRecords(
                    med.getFertilitySubRecords()
                            .stream()
                            .map(fertilitySubRecordService::mapToCompleteDTO)
                            .toList()
            );
            dto.setObstetricsRecords(
                    med.getObstetricsRecords()
                            .stream()
                            .map(obstetricsRecordService::mapToCompleteDTO)
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
    public String toHtmlStructured(long id) {
        MedicalRecordDTO medicalRecord = this.getMedicalRecord(id);
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading
        html.append("<h2>Medical Record</h2>");

        // Medical Backgrounds Section
        html.append("<h3>Medical Backgrounds</h3>");
        if (medicalRecord.getMedicalBackgrounds() != null && !medicalRecord.getMedicalBackgrounds().isEmpty()) {
            html.append(medicalBackgroundService.toHtmlTablesByBackgroundType(medicalRecord.getMedicalBackgrounds()));
        } else {
            html.append("<p>No medical backgrounds available</p>");
        }
        html.append("<br>");

        // Gynecology Sub-Records Section
        html.append("<h3>Gynecology Sub-Records</h3>");
        if (medicalRecord.getGynecologySubRecords() != null && !medicalRecord.getGynecologySubRecords().isEmpty()) {
            for (GynecologySubRecordDTO gynecologyRecord : medicalRecord.getGynecologySubRecords()) {
                html.append(gynecologySubRecordService.toHtmlStructured(gynecologyRecord));
            }
        } else {
            html.append("<p>No gynecology sub-records available</p>");
        }
        html.append("<br>");

        // Fertility Sub-Records Section
        html.append("<h3>Fertility Sub-Records</h3>");
        if (medicalRecord.getFertilitySubRecords() != null && !medicalRecord.getFertilitySubRecords().isEmpty()) {
            for (FertilitySubRecordDTO fertilityRecord : medicalRecord.getFertilitySubRecords()) {
                html.append(fertilitySubRecordService.toHtmlStructured(fertilityRecord));
            }
        } else {
            html.append("<p>No fertility sub-records available</p>");
        }
        html.append("<br>");

        // Obstetrics Records Section
        html.append("<h3>Obstetrics Records</h3>");
        if (medicalRecord.getObstetricsRecords() != null && !medicalRecord.getObstetricsRecords().isEmpty()) {
            for (ObstetricsRecordDTO obstetricsRecord : medicalRecord.getObstetricsRecords()) {
                html.append(obstetricsRecordService.toHtmlStructured(obstetricsRecord));
            }
        } else {
            html.append("<p>No obstetrics records available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
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
                .medicalBackgrounds(dto.getMedicalBackgrounds().stream().map(medicalBackgroundService::mapToEntity).toList())
                .comment(dto.getComment())
                .build();
    }

}
