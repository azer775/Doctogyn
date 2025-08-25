package org.example.analyse.repositories;

import org.example.analyse.Models.entities.SpermAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpermAnalysisRepository extends JpaRepository<SpermAnalysis, Long> {
    List<SpermAnalysis> findByConsultationId(Long consultationId);
}