package org.example.analyse.repositories;

import org.example.analyse.Models.entities.Bacteriology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BacteriologyRepository extends JpaRepository<Bacteriology, Long> {
    List<Bacteriology> findByConsultationId(Long consultationId);
}