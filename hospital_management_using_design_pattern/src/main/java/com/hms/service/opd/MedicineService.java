package com.hms.service.opd;

import com.hms.dto.AddMedicineRequestDTO;
import com.hms.dto.MedicineDTO;

import java.util.List;

public interface MedicineService {
    MedicineDTO addMedicine(AddMedicineRequestDTO request);
    List<MedicineDTO> getAllMedicines();
    MedicineDTO getMedicineById(String id);
    MedicineDTO updateStock(String id, int newStock);
}
