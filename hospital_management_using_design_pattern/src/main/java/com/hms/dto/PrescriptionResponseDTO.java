package com.hms.dto;

import com.hms.enums.opd.PrescriptionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionResponseDTO {
    private String id;
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private LocalDateTime createdAt;
    private PrescriptionStatus status;
    private List<PrescriptionItemDTO> items;
}
