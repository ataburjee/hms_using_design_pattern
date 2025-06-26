package com.hms.service.opd;

import com.hms.dto.PrescriptionRequestDTO;
import com.hms.dto.PrescriptionResponseDTO;

import java.util.List;

public interface PrescriptionService {
    PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request);
    List<PrescriptionResponseDTO> getPrescriptionsByPatientId(String patientId);
    List<PrescriptionResponseDTO> getPrescriptionsByDoctorId(String doctorId);

}
