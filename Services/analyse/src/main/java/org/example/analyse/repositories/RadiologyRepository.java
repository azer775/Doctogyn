package org.example.analyse.repositories;

import org.example.analyse.Models.entities.Radiology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadiologyRepository extends JpaRepository<Radiology, Long> {
    List<Radiology> findByConsultationId(Long consultationId);
}