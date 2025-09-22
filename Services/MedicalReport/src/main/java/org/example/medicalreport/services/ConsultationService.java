package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.ConsultationDTO;
import org.example.medicalreport.Models.DTOs.EchographieDTO;
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
import java.util.Objects;
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
    @Autowired
    EchographieService echographieService;
    @Autowired
    Analyse analyseService;

    public ConsultationDTO createConsultation(ConsultationDTO dto) {
        Consultation consultation = mapToEntity(dto);
        Consultation saved = consultationRepository.save(consultation);
        System.out.println("*****analyses******"+dto.getExtractionAnalyses());
        if (dto.getExtractionAnalyses() != null) {
            System.out.println("inside if");
            analyseService.test(dto.getExtractionAnalyses(), saved.getId());
        }
        return mapToDTO(saved);
    }

    public ConsultationDTO getConsultation(Long id) {
        Optional<Consultation> consultation = consultationRepository.findById(id);
        ConsultationDTO dto=consultation.map(this::mapToDTO).orElse(null);
        if(dto!=null) {
            dto.setExtractionAnalyses(analyseService.getByConsultation(id));
            return dto;
        }
        return null;
    }

    public List<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ConsultationDTO updateConsultation(Long id, ConsultationDTO dto) {
        Optional<Consultation> existing = consultationRepository.findById(id);
        if (existing.isPresent()) {
            Consultation consultation = mapToEntity(dto);
            consultation.setId(id); // Ensure the ID is preserved for update
            System.out.println("*****analyses******"+dto.getExtractionAnalyses());
            if (dto.getExtractionAnalyses() != null) {
                System.out.println("inside if");
                System.out.println(analyseService.update(dto.getExtractionAnalyses()));
            }
            System.out.println("before"+consultation.getEchographies());
            Consultation updated = consultationRepository.save(consultation);
            System.out.println("after"+updated.getEchographies());
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
                .echographies(consultation.getEchographies().stream().map(echographieService::mapToDTO).toList())
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
                .echographies(dto.getEchographies().stream().map(echographieService::mapToEntity).toList())
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
    public String toHtmlStructured(ConsultationDTO consultation) {
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading with date and type
        html.append("<h3>Consultation - ")
                .append(consultation.getDate() != null ? consultation.getDate() : "N/A")
                .append(" (")
                .append(consultation.getConsultationType() != null ? consultation.getConsultationType() : "N/A")
                .append(")</h3>");

        // General Section
        html.append("<h4>General</h4>");
        html.append("<p><strong>Signs Negated:</strong> ").append(consultation.getSignsNegates() != null ? consultation.getSignsNegates() : "N/A").append("</p>");
        html.append("<br>");

        // Physical Examination Section
        html.append("<h4>Physical Examination</h4>");
        html.append("<p><strong>Weight (kg):</strong> ").append(consultation.getWeight()).append("</p>");
        html.append("<p><strong>Length (cm):</strong> ").append(consultation.getLength()).append("</p>");
        html.append("<p><strong>BMI:</strong> ").append(consultation.getBmi()).append("</p>");
        html.append("<br>");

        // Gynecology Section
        html.append("<h4>Gynecology</h4>");
        html.append("<p><strong>Breasts:</strong> ").append(consultation.getBreasts() != null ? consultation.getBreasts() : "N/A").append("</p>");
        html.append("<p><strong>Vagina:</strong> ").append(consultation.getVagina() != null ? consultation.getVagina() : "N/A").append("</p>");
        html.append("<p><strong>Examination:</strong> ").append(consultation.getExamination() != null ? consultation.getExamination() : "N/A").append("</p>");
        html.append("<br>");

        // Echographies Section
        html.append("<h4>Echographies</h4>");
        if (consultation.getEchographies() != null && !consultation.getEchographies().isEmpty()) {
            for (EchographieDTO echographie : consultation.getEchographies()) {
                html.append(echographie.toHtmlStructured());
            }
        } else {
            html.append("<p>No echographies available</p>");
        }
        html.append("<br>");

        // Analyses Section
        html.append("<h4>Analyses</h4>");
        String analyses = analyseService.getHtmlAnalyses(consultation.getId());
        if (!Objects.equals(analyses, "<div></div>")) {
            html.append(analyses);
        } else {
            html.append("<p>No analyses available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }

}
