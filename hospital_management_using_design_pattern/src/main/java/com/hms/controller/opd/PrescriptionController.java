package com.hms.controller.opd;

import com.hms.dto.PrescriptionRequestDTO;
import com.hms.dto.PrescriptionResponseDTO;
import com.hms.model.ipd.Prescription;
import com.hms.repository.opd.PrescriptionRepository;
import com.hms.service.opd.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final PrescriptionRepository repo;

    @PostMapping("/create")
    public ResponseEntity<PrescriptionResponseDTO> create(@RequestBody PrescriptionRequestDTO request) {
        return ResponseEntity.ok(prescriptionService.createPrescription(request));
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatientId(patientId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionResponseDTO>> getByDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionsByDoctorId(doctorId));
    }

    //For test
    @GetMapping()
    public ResponseEntity<List<Prescription>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

}
