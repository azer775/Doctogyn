package org.example.analyse.Services;

import org.example.analyse.Models.entities.Serology;
import org.example.analyse.repositories.SerologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerologyService {

    @Autowired
    private SerologyRepository serologyRepository;

    public List<Serology> findAll() {
        return serologyRepository.findAll();
    }

    public Optional<Serology> findById(Long id) {
        return serologyRepository.findById(id);
    }

    public Serology save(Serology serology) {
        return serologyRepository.save(serology);
    }

    public Serology update(Long id, Serology serologyDetails) {
        Optional<Serology> serologyOptional = serologyRepository.findById(id);
        if (serologyOptional.isEmpty()) {
            throw new RuntimeException("Serology not found with id: " + id);
        }
        Serology serology = serologyOptional.get();
        serology.setDate(serologyDetails.getDate());
        serology.setType(serologyDetails.getType());
        serology.setValue(serologyDetails.getValue());
        serology.setInterpretation(serologyDetails.getInterpretation());
        serology.setComment(serologyDetails.getComment());
        serology.setConsultationId(serologyDetails.getConsultationId());
        return serologyRepository.save(serology);
    }

    public void deleteById(Long id) {
        serologyRepository.deleteById(id);
    }

    public List<Serology> findByConsultationId(Long consultationId) {
        return serologyRepository.findByConsultationId(consultationId);
    }

    public String toHtmlTable(List<Serology> serologies) {
        if (serologies == null || serologies.isEmpty()) {
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
        for (Serology serology : serologies) {
            html.append(serology.toHtmlRow());
        }

        html.append("</table>");
        return html.toString();
    }
}
