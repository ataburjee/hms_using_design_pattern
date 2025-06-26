package com.hms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItemDTO {
    private Integer serialNo;
    private String medicineName;
    private String dosage;
    private String frequency;
    private int durationInDays;
}
