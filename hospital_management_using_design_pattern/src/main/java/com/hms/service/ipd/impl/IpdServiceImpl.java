package com.hms.service.ipd.impl;

import com.hms.enums.ipd.AdmissionStatus;
import com.hms.enums.ipd.WardType;
import com.hms.model.opd.Admission;
import com.hms.model.opd.Bed;
import com.hms.model.opd.Ward;
import com.hms.repository.ipd.AdmissionRepository;
import com.hms.repository.ipd.BedRepository;
import com.hms.repository.ipd.WardRepository;
import com.hms.service.command.AdmitPatientCommand;
import com.hms.service.command.DischargePatientCommand;
import com.hms.service.ipd.IpdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class IpdServiceImpl implements IpdService {

    private final WardRepository wardRepo;
    private final BedRepository bedRepo;
    private final AdmissionRepository admissionRepo;

    @Override
    public Ward addWardWithBeds(String name, String type, int totalBeds) {
        Ward ward = Ward.builder()
                .name(name)
                .type(WardType.valueOf(type.toUpperCase()))
                .totalBeds(totalBeds)
                .build();

        Ward savedWard = wardRepo.save(ward);

        // Generate beds
        ArrayList<Bed> beds = new ArrayList<>();
        for (int i = 1; i <= totalBeds; i++) {
            beds.add(Bed.builder()
                    .bedNumber("B" + i)
                    .occupied(false)
                    .ward(savedWard)
                    .build());
        }

        savedWard.setBeds(bedRepo.saveAll(beds));
        return wardRepo.save(savedWard);
    }

    @Override
    public Admission admitPatient(String patientId, String doctorId, Long wardId) {
        AdmitPatientCommand command = new AdmitPatientCommand(patientId, doctorId, wardId, admissionRepo, bedRepo);
        command.execute();
        return admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.ADMITTED).get();
    }

    @Override
    public Admission dischargePatient(String patientId) {
        DischargePatientCommand command = new DischargePatientCommand(patientId, admissionRepo, bedRepo);
        command.execute();
        return admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.DISCHARGED).get();
    }

    /*@Override
    public Admission admitPatient(String patientId, String doctorId, Long wardId) {
        // Check if already admitted
        admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.ADMITTED)
                .ifPresent(a -> { throw new RuntimeException("Already admitted."); });

        Bed freeBed = bedRepo.findFirstByWardIdAndOccupiedFalse(wardId)
                .orElseThrow(() -> new RuntimeException("No free beds in ward"));

        freeBed.setOccupied(true);
        bedRepo.save(freeBed);

        Admission admission = Admission.builder()
                .id(Utility.generateId(Utility.ADMISSION))
                .patientId(patientId)
                .doctorId(doctorId)
                .bed(freeBed)
                .admittedAt(LocalDateTime.now())
                .status(AdmissionStatus.ADMITTED)
                .build();

        return admissionRepo.save(admission);
    }

    @Override
    public Admission dischargePatient(String patientId) {
        Admission admission = admissionRepo.findByPatientIdAndStatus(patientId, AdmissionStatus.ADMITTED)
                .orElseThrow(() -> new RuntimeException("Patient not admitted"));

        LocalDateTime now = LocalDateTime.now();
        admission.setStatus(AdmissionStatus.DISCHARGED);
        admission.setDischargedAt(now);

        // 1️⃣ Get ward type from the admission
        WardType wardType = admission.getBed().getWard().getType();

        // 2️⃣ Get billing strategy using the factory
        RoomChargeStrategy strategy = roomChargeStrategyFactory.getStrategy(wardType);

        // 3️⃣ Calculate total stay charges
        double totalRoomCharge = strategy.calculateCharge(admission.getAdmittedAt(), now);

        // 4️⃣ Print or save (you can persist to DB later)
        System.out.println("💰 Room Bill for Patient " + patientId + " in " + wardType + ": ₹" + totalRoomCharge);

        // 5️⃣ Mark bed as free
        Bed bed = admission.getBed();
        bed.setOccupied(false);
        bedRepo.save(bed);

        // 6️⃣ Save updated admission
        return admissionRepo.save(admission);
    }*/

}
