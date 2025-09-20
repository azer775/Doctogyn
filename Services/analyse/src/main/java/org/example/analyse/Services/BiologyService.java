package org.example.analyse.Services;

import org.example.analyse.Models.entities.Biology;
import org.example.analyse.repositories.BiologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiologyService {

    @Autowired
    private BiologyRepository biologyRepository;

    public List<Biology> findAll() {
        return biologyRepository.findAll();
    }

    public Optional<Biology> findById(Long id) {
        return biologyRepository.findById(id);
    }

    public Biology save(Biology biology) {
        return biologyRepository.save(biology);
    }

    public Biology update(Long id, Biology biologyDetails) {
        Optional<Biology> biologyOptional = biologyRepository.findById(id);
        if (biologyOptional.isEmpty()) {
            throw new RuntimeException("Biology not found with id: " + id);
        }
        Biology biology = biologyOptional.get();
        biology.setDate(biologyDetails.getDate());
        biology.setType(biologyDetails.getType());
        biology.setValue(biologyDetails.getValue());
        biology.setInterpretation(biologyDetails.getInterpretation());
        biology.setComment(biologyDetails.getComment());
        biology.setConsultationId(biologyDetails.getConsultationId());
        return biologyRepository.save(biology);
    }

    public void deleteById(Long id) {
        biologyRepository.deleteById(id);
    }

    public List<Biology> findByConsultationId(Long consultationId) {
        return biologyRepository.findByConsultationId(consultationId);
    }
    public String toHtmlRow(Biology biology) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");
        html.append("<td>").append(biology.getDate() != null ? biology.getDate() : "N/A").append("</td>");
        html.append("<td>").append(biology.getType() != null ? biology.getType() : "N/A").append("</td>");
        html.append("<td>").append(biology.getValue()).append("</td>");
        html.append("<td>").append(biology.getInterpretation() != null ? biology.getInterpretation() : "N/A").append("</td>");
        html.append("<td>").append(biology.getComment() != null ? biology.getComment() : "N/A").append("</td>");
        html.append("</tr>");

        return html.toString();
    }
    public String toHtmlTable(List<Biology> biologies) {
        if (biologies == null || biologies.isEmpty()) {
            return "<table border=\"1\"><tr><td colspan=\"5\">No data available</td></tr></table>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\">");

        // Header row
        html.append("<tr>");
        html.append("<th>Date</th>");
        html.append("<th>Type</th>");
        html.append("<th>Value</th>");
        html.append("<th>Interpretation</th>");
        html.append("<th>Comment</th>");
        html.append("</tr>");

        // Data rows
        for (Biology biology : biologies) {
            html.append(biology.toHtmlRow());
        }

        html.append("</table>");
        return html.toString();
    }
}
