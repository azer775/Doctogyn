package org.example.medicalreport.services;

import org.example.medicalreport.Models.entities.Resume;
import org.example.medicalreport.repositories.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

    public Resume add(Resume resume) {
        return resumeRepository.save(resume);
    }
    public Resume update(Resume resume) {
        return resumeRepository.save(resume);
    }
    public Resume findByMedicalRecordId(Long medicalRecordId) {
        return resumeRepository.findOneByMedicalRecordId(medicalRecordId).orElse(new Resume());
    }
}
