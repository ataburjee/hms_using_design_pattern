package com.hms.repository.opd;

import com.hms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
    List<Patient> findByFullNameContainingIgnoreCaseOrContactNumberContaining(String q1, String q2);
}
