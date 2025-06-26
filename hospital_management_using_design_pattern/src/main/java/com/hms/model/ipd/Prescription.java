package com.hms.model.ipd;

import com.hms.enums.opd.DispenseStatus;
import com.hms.enums.opd.PrescriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    private String id;

    private String appointmentId;
    private String doctorId;
    private String patientId;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL)
    private List<PrescriptionItem> items;

    @Enumerated(EnumType.STRING)
    private DispenseStatus dispenseStatus = DispenseStatus.NOT_DISPENSED;
}
