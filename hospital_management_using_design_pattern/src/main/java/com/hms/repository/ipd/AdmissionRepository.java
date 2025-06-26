package com.hms.repository.ipd;

import com.hms.enums.ipd.AdmissionStatus;
import com.hms.model.opd.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, String> {
    Optional<Admission> findByPatientIdAndStatus(String patientId, AdmissionStatus status);
}
