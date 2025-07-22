package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.Echographie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EchographieRepository extends JpaRepository<Echographie, Long> {
}