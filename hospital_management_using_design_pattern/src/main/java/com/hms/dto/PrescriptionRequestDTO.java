package com.hms.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionRequestDTO {
    private String appointmentId;
    private String doctorId;
    private String patientId;

    private List<PrescriptionItemDTO> items;
}
