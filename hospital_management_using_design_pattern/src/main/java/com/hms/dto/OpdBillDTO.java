package com.hms.dto;

import com.hms.enums.opd.BillingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpdBillDTO {
    private String id;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private double consultationFee;
    private double medicineCharges;
    private double totalAmount;
    private BillingStatus billingStatus;
    private LocalDateTime createdAt;
}
