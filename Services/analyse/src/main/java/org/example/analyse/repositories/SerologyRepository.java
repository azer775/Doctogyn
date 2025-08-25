package org.example.analyse.repositories;

import org.example.analyse.Models.entities.Serology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerologyRepository extends JpaRepository<Serology, Long> {
    List<Serology> findByConsultationId(Long consultationId);
}