package com.hms.service.command;

import com.hms.enums.ipd.AdmissionStatus;
import com.hms.model.opd.Admission;
import com.hms.model.opd.Bed;
import com.hms.repository.ipd.AdmissionRepository;
import com.hms.repository.ipd.BedRepository;
import com.hms.service.factory.RoomChargeStrategyFactory;
import com.hms.strategy.ipd.RoomChargeStrategy;

import java.time.LocalDateTime;

// DO NOT make it a Spring Bean
public class DischargePatientCommand implements IpdCommand {

    private final String patientId;
    private final AdmissionRepository admissionRepo;
    private final BedRepository bedRepo;

    public DischargePatientCommand(String patientId, AdmissionRepository admissionRepo, BedRepository bedRepo) {
        this.patientId = patientId;
        this.admissionRepo = admissionRepo;
        this.bedRepo = bedRepo;
    }

    @Override
    public void execute() {
        Admission admission = admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.ADMITTED)
                .orElseThrow(() -> new RuntimeException("Patient not admitted"));

        LocalDateTime now = LocalDateTime.now();
        admission.setDischargedAt(now);
        admission.setStatus(AdmissionStatus.DISCHARGED);

        Bed bed = admission.getBed();
        bed.setOccupied(false);
        bedRepo.save(bed);

        RoomChargeStrategy strategy = RoomChargeStrategyFactory.getStrategy(bed.getWard().getType());
        double bill = strategy.calculateCharge(admission.getAdmittedAt(), now);

        System.out.println("💰 Room charge for patient " + patientId + ": ₹" + bill);

        admissionRepo.save(admission);
    }
}
