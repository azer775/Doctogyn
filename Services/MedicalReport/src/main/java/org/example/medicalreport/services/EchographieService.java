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
    public EchographieDTO mapToDTO(Echographie echographie) {
        return EchographieDTO.builder()
                .id(echographie.getId())
                .date(echographie.getDate())
                .report(echographie.getReport())
                .cycleDay(echographie.getCycleDay())
                .condition(echographie.getCondition())
                .uterusSize(echographie.getUterusSize())
                .uterusLength(echographie.getUterusLength())
                .uterusWidth(echographie.getUterusWidth())
                .myometre(echographie.getMyometre())
                .myomesNumber(echographie.getMyomesNumber())
                .endometriumThickness(echographie.getEndometriumThickness())
                .comment(echographie.getComment())
                .ovaryR(echographie.getOvaryR())
                .ovaryRSize(echographie.getOvaryRSize())
                .cystSizeOR(echographie.getCystSizeOR())
                .diagnosticpresumptionsOR(echographie.getDiagnosticpresumptionsOR())
                .ovaryRComment(echographie.getOvaryRComment())
                .ovaryL(echographie.getOvaryL())
                .ovaryLSize(echographie.getOvaryLSize())
                .cystSizeOL(echographie.getCystSizeOL())
                .diagnosticpresumptionsOL(echographie.getDiagnosticpresumptionsOL())
                .ovaryLComment(echographie.getOvaryLComment())
                .pelvicMR(echographie.getPelvicMR())
                .pmRSize(echographie.getPmRSize())
                .pelvicdiagnosticpresumptionsR(echographie.getPelvicdiagnosticpresumptionsR())
                .pmRComment(echographie.getPmRComment())
                .pelvicML(echographie.getPelvicML())
                .pmLSize(echographie.getPmLSize())
                .pelvicdiagnosticpresumptionsL(echographie.getPelvicdiagnosticpresumptionsL())
                .pmLComment(echographie.getPmLComment())
                .consultationId(echographie.getConsultation() != null ? echographie.getConsultation().getId() : null)
                .build();
    }

    public Echographie mapToEntity(EchographieDTO dto) {
        Echographie echographie = Echographie.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .report(dto.getReport())
                .cycleDay(dto.getCycleDay())
                .condition(dto.getCondition())
                .uterusSize(dto.getUterusSize())
                .uterusLength(dto.getUterusLength())
                .uterusWidth(dto.getUterusWidth())
                .myometre(dto.getMyometre())
                .myomesNumber(dto.getMyomesNumber())
                .endometriumThickness(dto.getEndometriumThickness())
                .comment(dto.getComment())
                .ovaryR(dto.getOvaryR())
                .ovaryRSize(dto.getOvaryRSize())
                .cystSizeOR(dto.getCystSizeOR())
                .diagnosticpresumptionsOR(dto.getDiagnosticpresumptionsOR())
                .ovaryRComment(dto.getOvaryRComment())
                .ovaryL(dto.getOvaryL())
                .ovaryLSize(dto.getOvaryLSize())
                .cystSizeOL(dto.getCystSizeOL())
                .diagnosticpresumptionsOL(dto.getDiagnosticpresumptionsOL())
                .ovaryLComment(dto.getOvaryLComment())
                .pelvicMR(dto.getPelvicMR())
                .pmRSize(dto.getPmRSize())
                .pelvicdiagnosticpresumptionsR(dto.getPelvicdiagnosticpresumptionsR())
                .pmRComment(dto.getPmRComment())
                .pelvicML(dto.getPelvicML())
                .pmLSize(dto.getPmLSize())
                .pelvicdiagnosticpresumptionsL(dto.getPelvicdiagnosticpresumptionsL())
                .pmLComment(dto.getPmLComment())
                .build();
        if (dto.getConsultationId() != null) {
            Optional<Consultation> consultation = consultationRepository.findById(dto.getConsultationId());
            consultation.ifPresent(echographie::setConsultation);
            System.out.println("Consultation set for Echographie: " + echographie.getConsultation());
        }
        return echographie;
    }
}
