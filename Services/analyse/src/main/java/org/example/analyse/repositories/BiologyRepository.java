package org.example.analyse.repositories;

import org.example.analyse.Models.entities.Biology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiologyRepository extends JpaRepository<Biology, Long> {
    List<Biology> findByConsultationId(Long consultationId);
}