package org.example.user.repository;

import org.example.user.model.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);  // Find by email
    Doctor findByEmailAndPwd(String email, String pwd);  // Find by email and password (use with care)
    Doctor findByIdun(String idun);  // Find by idun
}
