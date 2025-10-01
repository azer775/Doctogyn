package org.example.user.repository;

import org.example.user.model.entities.AbbreviationDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbbreviationRepository extends JpaRepository<AbbreviationDefinition, Long> {
    List<AbbreviationDefinition> findByDoctorId(int doctorId);
}
