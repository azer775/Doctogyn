package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.EchographieDTO;
import org.example.medicalreport.Models.entities.Consultation;
import org.example.medicalreport.Models.entities.Echographie;
import org.example.medicalreport.repositories.ConsultationRepository;
import org.example.medicalreport.repositories.EchographieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EchographieService {
    @Autowired
    private EchographieRepository echographieRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public EchographieDTO createEchographie(EchographieDTO dto) {
        Echographie echographie = mapToEntity(dto);
        Echographie saved = echographieRepository.save(echographie);
        return mapToDTO(saved);
    }

    public EchographieDTO getEchographie(Long id) {
        Optional<Echographie> echographie = echographieRepository.findById(id);
        return echographie.map(this::mapToDTO).orElse(null);
    }

    public List<EchographieDTO> getAllEchographies() {
        return echographieRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EchographieDTO updateEchographie(Long id, EchographieDTO dto) {
        Optional<Echographie> existing = echographieRepository.findById(id);
        if (existing.isPresent()) {
            Echographie echographie = mapToEntity(dto);
            echographie.setId(id); // Ensure the ID is preserved for update
            Echographie updated = echographieRepository.save(echographie);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteEchographie(Long id) {
        echographieRepository.deleteById(id);
    }

    private EchographieDTO mapToDTO(Echographie echographie) {
        return EchographieDTO.builder()
                .id(echographie.getId())
                .size(echographie.getSize())
                .date(echographie.getDate())
                .report(echographie.getReport())
                .cycleDay(echographie.getCycleDay())
                .condition(echographie.getCondition())
                .uterusSize(echographie.getUterusSize())
                .endometrium(echographie.getEndometrium())
                .myometre(echographie.getMyometre())
                .ROSize(echographie.getROSize())
                .ROComment(echographie.getROComment())
                .LOComment(echographie.getLOComment())
                .consultationId(echographie.getConsultation() != null ? echographie.getConsultation().getId() : null)
                .build();
    }

    private Echographie mapToEntity(EchographieDTO dto) {
        Echographie echographie = Echographie.builder()
                .size(dto.getSize())
                .date(dto.getDate())
                .report(dto.getReport())
                .cycleDay(dto.getCycleDay())
                .condition(dto.getCondition())
                .uterusSize(dto.getUterusSize())
                .endometrium(dto.getEndometrium())
                .myometre(dto.getMyometre())
                .ROSize(dto.getROSize())
                .ROComment(dto.getROComment())
                .LOComment(dto.getLOComment())
                .build();
        if (dto.getConsultationId() != null) {
            Optional<Consultation> consultation = consultationRepository.findById(dto.getConsultationId());
            consultation.ifPresent(echographie::setConsultation);
        }
        return echographie;
    }
}
