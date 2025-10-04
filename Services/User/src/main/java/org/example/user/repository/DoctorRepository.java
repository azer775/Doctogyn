package org.example.user.repository;

import org.example.user.model.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);  // Find by email
    Doctor findByEmailAndPwd(String email, String pwd);  // Find by email and password (use with care)
    Doctor findByIdun(String idun);  // Find by idun
    @Query("SELECT d.id FROM Doctor d WHERE d.cabinet.id = :cabinetId AND (d.role = 'DOCTOR' OR d.role = 'ADMIN')")
    List<Integer> findByCabinetId(int cabinetId);  // Find doctors by cabinet ID
}
