package org.example.medicalreport.repositories;

import org.example.medicalreport.Models.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
   List<Appointment> findByCabinetId(long cabinetId);
}
