package com.hms.model.ipd;

import com.hms.enums.opd.BillingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "opd_bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpdBill {

    @Id
    private String id;

    private String appointmentId;
    private String patientId;
    private String doctorId;

    private double consultationFee;
    private double medicineCharges;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    private LocalDateTime createdAt;
}
