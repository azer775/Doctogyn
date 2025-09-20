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

    public String toHtmlTable(List<Bacteriology> bacteriologyList) {
        if (bacteriologyList == null || bacteriologyList.isEmpty()) {
            return "<table border=\"1\"><tr><td colspan=\"5\">No data available</td></tr></table>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\">");

        // Header row
        html.append("<tr>");
        html.append("<th>Date</th>");
        html.append("<th>Type</th>");
        html.append("<th>Germs</th>");
        html.append("<th>Interpretation</th>");
        html.append("<th>Comment</th>");
        html.append("</tr>");

        // Data rows
        for (Bacteriology bacteriology : bacteriologyList) {
            html.append(bacteriology.toHtmlRow());
        }

        html.append("</table>");
        return html.toString();
    }
}

