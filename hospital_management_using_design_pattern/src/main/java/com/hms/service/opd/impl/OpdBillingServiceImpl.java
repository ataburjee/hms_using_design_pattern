package com.hms.service.opd.impl;

import com.hms.dto.GenerateOpdBillRequestDTO;
import com.hms.dto.OpdBillDTO;
import com.hms.enums.opd.BillingStatus;
import com.hms.model.ipd.Appointment;
import com.hms.model.Doctor;
import com.hms.model.ipd.OpdBill;
import com.hms.model.ipd.Prescription;
import com.hms.repository.opd.AppointmentRepository;
import com.hms.repository.opd.DoctorRepository;
import com.hms.repository.opd.OpdBillRepository;
import com.hms.repository.opd.PrescriptionRepository;
import com.hms.service.opd.OpdBillingService;
import com.hms.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OpdBillingServiceImpl implements OpdBillingService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final OpdBillRepository billRepository;

    @Override
    public OpdBillDTO generateOpdBill(GenerateOpdBillRequestDTO request) {

        if (billRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new RuntimeException("Bill already exists for this appointment");
        }

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Prescription prescription = prescriptionRepository
                .findAll()
                .stream()
                .filter(p -> p.getAppointmentId().equals(request.getAppointmentId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        // 💰 1. Calculate consultation fee based on doctor type
        double consultationFee = switch (doctor.getDoctorType()) {
            case CONSULTATION -> 300;
            case SURGERY -> 700;
            case PHYSIOTHERAPY -> 1000;
            case RADIOLOGY -> 500;
            default -> throw new RuntimeException("Doctor type not accepted");
        };

        // 💊 2. Calculate medicine charges (dummy pricing logic ₹20 per med × days)
        double medicineCharges = prescription.getItems().stream()
                .mapToDouble(i -> 20 * i.getDurationInDays())
                .sum();

        // 📄 3. Create bill
        OpdBill bill = OpdBill.builder()
                .id(Utility.generateId(Utility.OPD))
                .appointmentId(request.getAppointmentId())
                .doctorId(request.getDoctorId())
                .patientId(request.getPatientId())
                .consultationFee(consultationFee)
                .medicineCharges(medicineCharges)
                .totalAmount(consultationFee + medicineCharges)
                .billingStatus(BillingStatus.UNPAID)
                .createdAt(LocalDateTime.now())
                .build();

        OpdBill saved = billRepository.save(bill);

        // 🔁 4. Return DTO
        return OpdBillDTO.builder()
                .id(saved.getId())
                .appointmentId(saved.getAppointmentId())
                .doctorId(saved.getDoctorId())
                .patientId(saved.getPatientId())
                .consultationFee(saved.getConsultationFee())
                .medicineCharges(saved.getMedicineCharges())
                .totalAmount(saved.getTotalAmount())
                .billingStatus(saved.getBillingStatus())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    @Override
    public OpdBillDTO markAsPaid(String billId) {
        OpdBill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (bill.getBillingStatus() == BillingStatus.PAID) {
            throw new RuntimeException("Bill already marked as PAID");
        }

        if (bill.getBillingStatus() == BillingStatus.CANCELLED) {
            throw new RuntimeException("Cannot pay a CANCELLED bill");
        }

        bill.setBillingStatus(BillingStatus.PAID);
        OpdBill updated = billRepository.save(bill);

        return OpdBillDTO.builder()
                .id(updated.getId())
                .appointmentId(updated.getAppointmentId())
                .doctorId(updated.getDoctorId())
                .patientId(updated.getPatientId())
                .consultationFee(updated.getConsultationFee())
                .medicineCharges(updated.getMedicineCharges())
                .totalAmount(updated.getTotalAmount())
                .billingStatus(updated.getBillingStatus())
                .createdAt(updated.getCreatedAt())
                .build();
    }

}
