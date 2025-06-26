package com.hms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookAppointmentRequestDTO {

    private String patientId;
    private String doctorId;
    private String timeSlotId;
    private boolean isOnline;
}
