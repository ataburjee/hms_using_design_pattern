package com.hms.service;

import com.hms.dto.PatientDTO;
import com.hms.model.Patient;

import java.util.List;

public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO);
    PatientDTO getPatientById(String id);
    List<PatientDTO> getAllPatients();
    PatientDTO updatePatient(String id, PatientDTO patientDTO);
    void deletePatient(String id);
    List<Patient> searchPatients(String q);
}
