package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.FertilitySubRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FertilitySubRecordRepository extends JpaRepository<FertilitySubRecord, Long> {
}