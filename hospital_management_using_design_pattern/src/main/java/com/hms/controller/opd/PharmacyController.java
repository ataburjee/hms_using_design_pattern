package com.hms.controller.opd;

import com.hms.service.opd.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacy")
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @PostMapping("/dispense/{prescriptionId}")
    public ResponseEntity<String> dispense(@PathVariable String prescriptionId) {
        return ResponseEntity.ok(pharmacyService.dispensePrescription(prescriptionId));
    }
}