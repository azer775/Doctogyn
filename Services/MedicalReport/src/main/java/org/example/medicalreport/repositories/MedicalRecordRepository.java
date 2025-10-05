package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByDoctorIdIn(List<Integer> userIds);
    //List<MedicalRecord> findByUserId(int userId);

}