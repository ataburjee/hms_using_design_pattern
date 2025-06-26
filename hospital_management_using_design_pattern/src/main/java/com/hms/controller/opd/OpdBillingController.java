package com.hms.controller.opd;

import com.hms.dto.GenerateOpdBillRequestDTO;
import com.hms.dto.OpdBillDTO;
import com.hms.service.opd.OpdBillingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
public class OpdBillingController {

    @Autowired
    private OpdBillingService opdBillingService;

    @PostMapping("/generate")
    public ResponseEntity<OpdBillDTO> generate(@RequestBody GenerateOpdBillRequestDTO request) {
        return ResponseEntity.ok(opdBillingService.generateOpdBill(request));
    }

    @PutMapping("/{billId}/pay")
    public ResponseEntity<OpdBillDTO> markPaid(@PathVariable String billId) {
        return ResponseEntity.ok(opdBillingService.markAsPaid(billId));
    }

}
