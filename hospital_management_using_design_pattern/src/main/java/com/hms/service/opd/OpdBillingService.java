package com.hms.service.opd;

import com.hms.dto.GenerateOpdBillRequestDTO;
import com.hms.dto.OpdBillDTO;

public interface OpdBillingService {
    OpdBillDTO generateOpdBill(GenerateOpdBillRequestDTO request);
    OpdBillDTO markAsPaid(String billId);
}
