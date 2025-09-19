package org.example.analyse.Services;

import org.example.analyse.Models.entities.BloodGroup;
import org.example.analyse.repositories.BloodGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodGroupService {

    @Autowired
    private BloodGroupRepository bloodGroupRepository;

    public List<BloodGroup> findAll() {
        return bloodGroupRepository.findAll();
    }

    public Optional<BloodGroup> findById(Long id) {
        return bloodGroupRepository.findById(id);
    }

    public BloodGroup save(BloodGroup bloodGroup) {
        return bloodGroupRepository.save(bloodGroup);
    }

    public BloodGroup update(Long id, BloodGroup bloodGroupDetails) {
        Optional<BloodGroup> bloodGroupOptional = bloodGroupRepository.findById(id);
        if (bloodGroupOptional.isEmpty()) {
            throw new RuntimeException("BloodGroup not found with id: " + id);
        }
        BloodGroup bloodGroup = bloodGroupOptional.get();
        bloodGroup.setDate(bloodGroupDetails.getDate());
        bloodGroup.setType(bloodGroupDetails.getType());
        bloodGroup.setComment(bloodGroupDetails.getComment());
        bloodGroup.setConsultationId(bloodGroupDetails.getConsultationId());
        return bloodGroupRepository.save(bloodGroup);
    }

    public void deleteById(Long id) {
        bloodGroupRepository.deleteById(id);
    }

    public List<BloodGroup> findByConsultationId(Long consultationId) {
        return bloodGroupRepository.findByConsultationId(consultationId);
    }
    public  String toHtmlRow(BloodGroup bloodGroup) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");

        html.append("<td>").append(bloodGroup.getDate() != null ? bloodGroup.getDate() : "N/A").append("</td>");
        html.append("<td>").append(bloodGroup.getType() != null ? bloodGroup.getType() : "N/A").append("</td>");
        html.append("<td>").append(bloodGroup.getComment() != null ? bloodGroup.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}
