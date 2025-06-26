package com.hms.service.opd.impl;

import com.hms.enums.opd.DispenseStatus;
import com.hms.model.ipd.Medicine;
import com.hms.model.ipd.Prescription;
import com.hms.model.ipd.PrescriptionItem;
import com.hms.repository.opd.MedicineRepository;
import com.hms.repository.opd.PrescriptionRepository;
import com.hms.service.opd.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyServiceImpl implements PharmacyService {

    private PrescriptionRepository prescriptionRepository;
    private MedicineRepository medicineRepository;

    @Override
        public String dispensePrescription(String prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (prescription.getDispenseStatus() == DispenseStatus.DISPENSED) {
            return "Already dispensed.";
        }

        for (PrescriptionItem item : prescription.getItems()) {
            Medicine medicine = medicineRepository.findByNameIgnoreCase(item.getMedicineName())
                    .orElseThrow(() -> new RuntimeException("Medicine not in inventory: " + item.getMedicineName()));

            int required = item.getDurationInDays();

            if (medicine.getStock() < required) {
                throw new RuntimeException("Insufficient stock for: " + medicine.getName());
            }

            medicine.setStock(medicine.getStock() - required);
            medicineRepository.save(medicine);
        }

        prescription.setDispenseStatus(DispenseStatus.DISPENSED);
        prescriptionRepository.save(prescription);

        return "Prescription dispensed successfully!";
    }
}
