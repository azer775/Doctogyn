package org.example.analyse.Services;

import org.example.analyse.Models.entities.Bacteriology;
import org.example.analyse.repositories.BacteriologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BacteriologyService {

    @Autowired
    private BacteriologyRepository bacteriologyRepository;

    public List<Bacteriology> findAll() {
        return bacteriologyRepository.findAll();
    }

    public Optional<Bacteriology> findById(Long id) {
        return bacteriologyRepository.findById(id);
    }

    public Bacteriology save(Bacteriology bacteriology) {
        return bacteriologyRepository.save(bacteriology);
    }

    public Bacteriology update(Long id, Bacteriology bacteriologyDetails) {
        Optional<Bacteriology> bacteriologyOptional = bacteriologyRepository.findById(id);
        if (bacteriologyOptional.isEmpty()) {
            throw new RuntimeException("Bacteriology not found with id: " + id);
        }
        Bacteriology bacteriology = bacteriologyOptional.get();
        bacteriology.setDate(bacteriologyDetails.getDate());
        bacteriology.setType(bacteriologyDetails.getType());
        bacteriology.setGerms(bacteriologyDetails.getGerms());
        bacteriology.setInterpretation(bacteriologyDetails.getInterpretation());
        bacteriology.setComment(bacteriologyDetails.getComment());
        bacteriology.setConsultationId(bacteriologyDetails.getConsultationId());
        return bacteriologyRepository.save(bacteriology);
    }

    public void deleteById(Long id) {
        bacteriologyRepository.deleteById(id);
    }

    public List<Bacteriology> findByConsultationId(Long consultationId) {
        return bacteriologyRepository.findByConsultationId(consultationId);
    }
    public String toHtmlRow(Bacteriology bacteriology) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");
        html.append("<td>").append(bacteriology.getDate() != null ? bacteriology.getDate() : "N/A").append("</td>");
        html.append("<td>").append(bacteriology.getType() != null ? bacteriology.getType() : "N/A").append("</td>");
        html.append("<td>")
                .append(bacteriology.getGerms() != null && !bacteriology.getGerms().isEmpty()
                        ? String.join(", ", bacteriology.getGerms().toString())
                        : "N/A")
                .append("</td>");
        html.append("<td>").append(bacteriology.getInterpretation() != null ? bacteriology.getInterpretation() : "N/A").append("</td>");
        html.append("<td>").append(bacteriology.getComment() != null ? bacteriology.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}

