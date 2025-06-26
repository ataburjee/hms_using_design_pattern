package com.hms.service.opd.impl;

import com.hms.dto.AddMedicineRequestDTO;
import com.hms.dto.MedicineDTO;
import com.hms.model.ipd.Medicine;
import com.hms.repository.opd.MedicineRepository;
import com.hms.service.opd.MedicineService;

import com.hms.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public MedicineDTO addMedicine(AddMedicineRequestDTO request) {
        // Check duplicate by name
        medicineRepository.findByNameIgnoreCase(request.getName()).ifPresent(med -> {
            throw new RuntimeException("Medicine already exists: " + request.getName());
        });

        Medicine medicine = Medicine.builder()
                .id(Utility.generateId(Utility.MEDICINE))
                .name(request.getName())
                .brand(request.getBrand())
                .dosageForm(request.getDosageForm())
                .strength(request.getStrength())
                .stock(request.getStock())
                .pricePerUnit(request.getPricePerUnit())
                .build();

        Medicine saved = medicineRepository.save(medicine);

        return mapToDTO(saved);
    }

    @Override
    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MedicineDTO getMedicineById(String id) {
        return medicineRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }

    @Override
    public MedicineDTO updateStock(String id, int newStock) {
        Medicine med = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        med.setStock(newStock);
        return mapToDTO(medicineRepository.save(med));
    }

    private MedicineDTO mapToDTO(Medicine m) {
        return MedicineDTO.builder()
                .id(m.getId())
                .name(m.getName())
                .brand(m.getBrand())
                .dosageForm(m.getDosageForm())
                .strength(m.getStrength())
                .stock(m.getStock())
                .pricePerUnit(m.getPricePerUnit())
                .build();
    }
}
