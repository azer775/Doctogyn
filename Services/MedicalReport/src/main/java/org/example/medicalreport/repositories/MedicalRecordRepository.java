package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.DTOs.Stat;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByDoctorIdIn(List<Integer> userIds);
    //List<MedicalRecord> findByUserId(int userId);
    @Query("SELECT DISTINCT NEW org.example.medicalreport.Models.DTOs.Stat(c.date, SIZE(c.echographies), mr.doctorId, c.consultationType) " +
            "FROM MedicalRecord mr " +
            "LEFT JOIN mr.gynecologySubRecords gsr " +
            "LEFT JOIN mr.fertilitySubRecords fsr " +
            "LEFT JOIN mr.obstetricsRecords or " +
            "LEFT JOIN gsr.consultations c1 " +
            "LEFT JOIN fsr.consultations c2 " +
            "LEFT JOIN or.consultations c3 " +
            "JOIN Consultation c ON c.id = COALESCE(c1.id, c2.id, c3.id) " +
            "WHERE mr.id = :medicalRecordId AND c.id IS NOT NULL")
    List<Stat> findConsultationStats(@Param("medicalRecordId") Long medicalRecordId);
}