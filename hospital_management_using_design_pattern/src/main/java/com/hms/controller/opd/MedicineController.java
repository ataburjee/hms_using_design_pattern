package com.hms.controller.opd;

import com.hms.dto.AddMedicineRequestDTO;
import com.hms.dto.MedicineDTO;
import com.hms.service.opd.MedicineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacy/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping("/add")
    public ResponseEntity<MedicineDTO> add(@RequestBody AddMedicineRequestDTO request) {
        return ResponseEntity.ok(medicineService.addMedicine(request));
    }

    @GetMapping
    public ResponseEntity<List<MedicineDTO>> getAll() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<MedicineDTO> updateStock(@PathVariable String id, @RequestParam int newStock) {
        return ResponseEntity.ok(medicineService.updateStock(id, newStock));
    }
}
