package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.MedicalBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface MedicalBackgroundRepository extends JpaRepository<MedicalBackground, Long> {
    List<MedicalBackground> findByMedicalRecordId(Long medicalRecordId);
}