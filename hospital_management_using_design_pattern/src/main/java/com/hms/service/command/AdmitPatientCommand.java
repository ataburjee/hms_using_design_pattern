package com.hms.service.command;

import com.hms.enums.ipd.AdmissionStatus;
import com.hms.model.opd.Admission;
import com.hms.model.opd.Bed;
import com.hms.repository.ipd.AdmissionRepository;
import com.hms.repository.ipd.BedRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class AdmitPatientCommand implements IpdCommand {

    private final String patientId;
    private final String doctorId;
    private final Long wardId;

    private final AdmissionRepository admissionRepo;
    private final BedRepository bedRepo;

    public AdmitPatientCommand(String patientId, String doctorId, Long wardId,
                               AdmissionRepository admissionRepo, BedRepository bedRepo) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.wardId = wardId;
        this.admissionRepo = admissionRepo;
        this.bedRepo = bedRepo;
    }

    @Override
    public void execute() {
        admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.ADMITTED)
                .ifPresent(a -> { throw new RuntimeException("Patient already admitted"); });

        Bed freeBed = bedRepo.findFirstByWardIdAndOccupiedFalse(wardId)
                .orElseThrow(() -> new RuntimeException("No free bed in ward"));

        freeBed.setOccupied(true);
        bedRepo.save(freeBed);

        Admission admission = Admission.builder()
                .patientId(patientId)
                .doctorId(doctorId)
                .bed(freeBed)
                .admittedAt(LocalDateTime.now())
                .status(AdmissionStatus.ADMITTED)
                .build();

        admissionRepo.save(admission);
        System.out.println("✅ Patient " + patientId + " admitted to bed " + freeBed.getBedNumber());
    }
}

