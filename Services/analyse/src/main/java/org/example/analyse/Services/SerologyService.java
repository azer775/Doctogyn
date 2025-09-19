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
    public  String toHtmlRow(Serology serology) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");

        html.append("<td>").append(serology.getDate() != null ? serology.getDate() : "N/A").append("</td>");
        html.append("<td>").append(serology.getType() != null ? serology.getType() : "N/A").append("</td>");
        html.append("<td>").append(serology.getValue()).append("</td>");
        html.append("<td>").append(serology.getInterpretation() != null ? serology.getInterpretation() : "N/A").append("</td>");
        html.append("<td>").append(serology.getComment() != null ? serology.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}
