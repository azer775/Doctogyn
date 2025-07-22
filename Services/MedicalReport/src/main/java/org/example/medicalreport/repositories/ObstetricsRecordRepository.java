package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.ObstetricsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObstetricsRecordRepository extends JpaRepository<ObstetricsRecord, Long> {
}