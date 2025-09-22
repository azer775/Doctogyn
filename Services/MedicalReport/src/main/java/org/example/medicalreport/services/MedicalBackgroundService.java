package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.MedicalBackgroundDTO;
import org.example.medicalreport.Models.entities.MedicalBackground;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.Models.enums.BackgroundType;
import org.example.medicalreport.repositories.MedicalBackgroundRepository;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
                .comment(medicalBackground.getComment())
                .date(medicalBackground.getDate())
                .backgroundType(medicalBackground.getBackgroundType())
                .medicalRecordId(medicalBackground.getMedicalRecord() != null ? medicalBackground.getMedicalRecord().getId() : null)
                .build();
    }

    public MedicalBackground mapToEntity(MedicalBackgroundDTO dto) {
        MedicalBackground medicalBackground = MedicalBackground.builder()
                .id(dto.getId())
                .familialPathology(dto.getFamilialPathology())
                .allergies(dto.getAllergies())
                .medicalPathology(dto.getMedicalPathology())
                .chirurgicalPathology(dto.getChirurgicalPathology())
                .surgicalApproach(dto.getSurgicalApproach())
                .comment(dto.getComment())
                .date(dto.getDate())
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
    public List<MedicalBackgroundDTO> updateMBList(List<MedicalBackgroundDTO> mbList) {
        List<MedicalBackground> list = mbList.stream().map(this::mapToEntity).toList();
        return medicalBackgroundRepository.saveAll(list).stream().map(this::mapToDTO).toList();
    }
    public String toHtmlTablesByBackgroundType(List<MedicalBackgroundDTO> backgrounds) {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Group by BackgroundType
        Map<BackgroundType, List<MedicalBackgroundDTO>> groupedByType = backgrounds.stream()
                .collect(Collectors.groupingBy(MedicalBackgroundDTO::getBackgroundType));

        // Familial Background Table
        html.append("<h3>Familial Background</h3>");
        List<MedicalBackgroundDTO> familialRecords = groupedByType.getOrDefault(BackgroundType.Familial, List.of());
        if (familialRecords.isEmpty()) {
            html.append("<table border=\"1\"><tr><td colspan=\"3\">No data available</td></tr></table>");
        } else {
            html.append("<table border=\"1\">");
            html.append("<tr><th>Date</th><th>Familial Pathology</th><th>Comment</th></tr>");
            for (MedicalBackgroundDTO record : familialRecords) {
                html.append("<tr>");
                html.append("<td>").append(record.getDate() != null ? record.getDate() : "N/A").append("</td>");
                html.append("<td>").append(record.getFamilialPathology() != null ? record.getFamilialPathology() : "N/A").append("</td>");
                html.append("<td>").append(record.getComment() != null ? record.getComment() : "N/A").append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        html.append("<br>");

        // Allergies Table
        html.append("<h3>Allergies</h3>");
        List<MedicalBackgroundDTO> allergyRecords = groupedByType.getOrDefault(BackgroundType.Allergies, List.of());
        if (allergyRecords.isEmpty()) {
            html.append("<table border=\"1\"><tr><td colspan=\"3\">No data available</td></tr></table>");
        } else {
            html.append("<table border=\"1\">");
            html.append("<tr><th>Date</th><th>Allergies</th><th>Comment</th></tr>");
            for (MedicalBackgroundDTO record : allergyRecords) {
                html.append("<tr>");
                html.append("<td>").append(record.getDate() != null ? record.getDate() : "N/A").append("</td>");
                html.append("<td>").append(record.getAllergies() != null ? record.getAllergies() : "N/A").append("</td>");
                html.append("<td>").append(record.getComment() != null ? record.getComment() : "N/A").append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        html.append("<br>");

        // Medical Background Table
        html.append("<h3>Medical Background</h3>");
        List<MedicalBackgroundDTO> medicalRecords = groupedByType.getOrDefault(BackgroundType.Medical, List.of());
        if (medicalRecords.isEmpty()) {
            html.append("<table border=\"1\"><tr><td colspan=\"3\">No data available</td></tr></table>");
        } else {
            html.append("<table border=\"1\">");
            html.append("<tr><th>Date</th><th>Medical Pathology</th><th>Comment</th></tr>");
            for (MedicalBackgroundDTO record : medicalRecords) {
                html.append("<tr>");
                html.append("<td>").append(record.getDate() != null ? record.getDate() : "N/A").append("</td>");
                html.append("<td>").append(record.getMedicalPathology() != null ? record.getMedicalPathology() : "N/A").append("</td>");
                html.append("<td>").append(record.getComment() != null ? record.getComment() : "N/A").append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        html.append("<br>");

        // Chirurgical Background Table
        html.append("<h3>Chirurgical Background</h3>");
        List<MedicalBackgroundDTO> chirurgicalRecords = groupedByType.getOrDefault(BackgroundType.Chirurgical, List.of());
        if (chirurgicalRecords.isEmpty()) {
            html.append("<table border=\"1\"><tr><td colspan=\"3\">No data available</td></tr></table>");
        } else {
            html.append("<table border=\"1\">");
            html.append("<tr><th>Date</th><th>Chirurgical Pathology</th><th>Comment</th></tr>");
            for (MedicalBackgroundDTO record : chirurgicalRecords) {
                html.append("<tr>");
                html.append("<td>").append(record.getDate() != null ? record.getDate() : "N/A").append("</td>");
                html.append("<td>").append(record.getChirurgicalPathology() != null ? record.getChirurgicalPathology() : "N/A").append("</td>");
                html.append("<td>").append(record.getComment() != null ? record.getComment() : "N/A").append("</td>");
                html.append("</tr>");
            }
            html.append("</table>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
}
