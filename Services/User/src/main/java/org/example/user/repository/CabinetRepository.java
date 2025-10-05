package org.example.user.repository;


import org.example.user.model.entities.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetRepository  extends JpaRepository<Cabinet, Long> {
}
