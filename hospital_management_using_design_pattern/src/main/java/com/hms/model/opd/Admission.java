package com.hms.model.opd;

import com.hms.enums.ipd.AdmissionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admission {

    @Id
    private String id;

    private String patientId;
    private String doctorId;
    private LocalDateTime admittedAt;
    private LocalDateTime dischargedAt;

    @Enumerated(EnumType.STRING)
    private AdmissionStatus status;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    private Bed bed;
}
