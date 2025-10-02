package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT r FROM Resume r WHERE r.medicalRecord.id = :medicalRecordId")
    Optional<Resume> findOneByMedicalRecordId(@Param("medicalRecordId") Long medicalRecordId);
}
