package com.hms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateOpdBillRequestDTO {
    private String appointmentId;
    private String patientId;
    private String doctorId;
}
