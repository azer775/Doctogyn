package org.example.analyse.repositories;

import org.example.analyse.Models.entities.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup, Long> {
    List<BloodGroup> findByConsultationId(Long consultationId);
}