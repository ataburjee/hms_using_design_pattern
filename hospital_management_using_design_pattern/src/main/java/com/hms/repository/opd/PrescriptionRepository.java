package com.hms.repository.opd;

import com.hms.model.ipd.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    List<Prescription> findByPatientId(String patientId);
    List<Prescription> findByDoctorId(String doctorId);
}
