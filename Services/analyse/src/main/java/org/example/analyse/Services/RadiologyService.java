package org.example.analyse.Services;

import org.example.analyse.Models.entities.Radiology;
import org.example.analyse.repositories.RadiologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RadiologyService {

    @Autowired
    private RadiologyRepository radiologyRepository;

    public List<Radiology> findAll() {
        return radiologyRepository.findAll();
    }

    public Optional<Radiology> findById(Long id) {
        return radiologyRepository.findById(id);
    }

    public Radiology save(Radiology radiology) {
        return radiologyRepository.save(radiology);
    }

    public Radiology update(Long id, Radiology radiologyDetails) {
        Optional<Radiology> radiologyOptional = radiologyRepository.findById(id);
        if (radiologyOptional.isEmpty()) {
            throw new RuntimeException("Radiology not found with id: " + id);
        }
        Radiology radiology = radiologyOptional.get();
        radiology.setDate(radiologyDetails.getDate());
        radiology.setType(radiologyDetails.getType());
        radiology.setConclusion(radiologyDetails.getConclusion());
        radiology.setComment(radiologyDetails.getComment());
        radiology.setConsultationId(radiologyDetails.getConsultationId());
        return radiologyRepository.save(radiology);
    }

    public void deleteById(Long id) {
        radiologyRepository.deleteById(id);
    }

    public List<Radiology> findByConsultationId(Long consultationId) {
        return radiologyRepository.findByConsultationId(consultationId);
    }
    public String toHtmlRow(Radiology radiology) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");

        html.append("<td>").append(radiology.getDate() != null ? radiology.getDate() : "N/A").append("</td>");
        html.append("<td>").append(radiology.getType() != null ? radiology.getType() : "N/A").append("</td>");
        html.append("<td>").append(radiology.getConclusion() != null ? radiology.getConclusion() : "N/A").append("</td>");
        html.append("<td>").append(radiology.getComment() != null ? radiology.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}
