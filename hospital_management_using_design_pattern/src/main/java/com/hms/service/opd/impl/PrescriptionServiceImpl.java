package com.hms.service.opd.impl;

import com.hms.dto.PrescriptionItemDTO;
import com.hms.dto.PrescriptionRequestDTO;
import com.hms.dto.PrescriptionResponseDTO;
import com.hms.enums.opd.PrescriptionStatus;
import com.hms.model.ipd.Prescription;
import com.hms.model.ipd.PrescriptionItem;
import com.hms.repository.opd.PrescriptionRepository;
import com.hms.service.opd.PrescriptionService;
import com.hms.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    @Override
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO request) {
        // Convert DTO items to entities
        List<PrescriptionItem> items = request.getItems().stream()
                .map(i -> PrescriptionItem.builder()
                        .id(Utility.generateId(Utility.PRESCRIPTION_ITEM))
                        .medicineName(i.getMedicineName())
                        .dosage(i.getDosage())
                        .frequency(i.getFrequency())
                        .durationInDays(i.getDurationInDays())
                        .build())
                .collect(Collectors.toList());

        // Build prescription
        Prescription prescription = Prescription.builder()
                .id(Utility.generateId(Utility.prescription))
                .appointmentId(request.getAppointmentId())
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .createdAt(LocalDateTime.now())
                .status(PrescriptionStatus.COMPLETED)
                .items(items)
                .build();

        // Link prescription to each item
        items.forEach(i -> i.setPrescription(prescription));

        AtomicInteger serialNo = new AtomicInteger();
        serialNo.getAndIncrement();

        // Save all
        Prescription saved = prescriptionRepository.save(prescription);

        // Return DTO
        return PrescriptionResponseDTO.builder()
                .id(saved.getId())
                .appointmentId(saved.getAppointmentId())
                .doctorId(saved.getDoctorId())
                .patientId(saved.getPatientId())
                .createdAt(saved.getCreatedAt())
                .status(saved.getStatus())
                .items(saved.getItems().stream().map(i -> PrescriptionItemDTO.builder()
                        .serialNo(serialNo.getAndIncrement())
                        .medicineName(i.getMedicineName())
                        .dosage(i.getDosage())
                        .frequency(i.getFrequency())
                        .durationInDays(i.getDurationInDays())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<PrescriptionResponseDTO> getPrescriptionsByPatientId(String patientId) {
        return prescriptionRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrescriptionResponseDTO> getPrescriptionsByDoctorId(String doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // 🔄 Reusable method
    private PrescriptionResponseDTO mapToResponseDTO(Prescription p) {
        return PrescriptionResponseDTO.builder()
                .id(p.getId())
                .appointmentId(p.getAppointmentId())
                .doctorId(p.getDoctorId())
                .patientId(p.getPatientId())
                .createdAt(p.getCreatedAt())
                .status(p.getStatus())
                .items(p.getItems().stream().map(i -> PrescriptionItemDTO.builder()
                        .medicineName(i.getMedicineName())
                        .dosage(i.getDosage())
                        .frequency(i.getFrequency())
                        .durationInDays(i.getDurationInDays())
                        .build()).collect(Collectors.toList()))
                .build();
    }

}