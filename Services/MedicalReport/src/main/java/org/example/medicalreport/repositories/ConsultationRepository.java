package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    @Query("SELECT c FROM Consultation c " +
            "LEFT JOIN c.obstetricsRecord or " +
            "LEFT JOIN c.gynecologySubRecord gsr " +
            "LEFT JOIN c.fertilitySubRecord fsr " +
            "WHERE (or.medicalRecord.id = :medicalRecordId " +
            "OR gsr.medicalRecord.id = :medicalRecordId " +
            "OR fsr.medicalRecord.id = :medicalRecordId) " +
            "AND c.updatedAt = (SELECT MAX(c2.updatedAt) FROM Consultation c2 " +
            "LEFT JOIN c2.obstetricsRecord or2 " +
            "LEFT JOIN c2.gynecologySubRecord gsr2 " +
            "LEFT JOIN c2.fertilitySubRecord fsr2 " +
            "WHERE (or2.medicalRecord.id = :medicalRecordId " +
            "OR gsr2.medicalRecord.id = :medicalRecordId " +
            "OR fsr2.medicalRecord.id = :medicalRecordId) " +
            "AND c2.updatedAt IS NOT NULL)")
    Optional<Consultation> findLatestConsultationByMedicalRecordId(@Param("medicalRecordId") Long medicalRecordId);
}