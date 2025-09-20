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


    public String toHtmlTable(List<Radiology> radiologies) {
        if (radiologies == null || radiologies.isEmpty()) {
            return "<table border=\"1\"><tr><td colspan=\"4\">No data available</td></tr></table>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\">");

        // Header row
        html.append("<tr>");
        html.append("<th>Date</th>");
        html.append("<th>Type</th>");
        html.append("<th>Conclusion</th>");
        html.append("<th>Comment</th>");
        html.append("</tr>");

        // Data rows
        for (Radiology radiology : radiologies) {
            html.append(radiology.toHtmlRow());
        }

        html.append("</table>");
        return html.toString();
    }
}
